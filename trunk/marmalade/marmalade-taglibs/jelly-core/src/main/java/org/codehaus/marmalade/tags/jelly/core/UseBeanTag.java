/* Created on Jun 23, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import java.util.Iterator;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttribute;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jelly.AbstractJellyMarmaladeTag;

public class UseBeanTag extends AbstractJellyMarmaladeTag implements TargetObjectOwner{
    
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VAR_ATTRIBUTE = "var";
    private Object target;

    public UseBeanTag(MarmaladeTagInfo tagInfo) {
        super(tagInfo);
    }

    protected void doExecute(MarmaladeExecutionContext context)
    throws MarmaladeExecutionException 
    {
        String var = (String)requireTagAttribute(VAR_ATTRIBUTE, String.class, context);
        Object classObj = requireTagAttribute(CLASS_ATTRIBUTE, context);
        
        Class objectClass = null;
        if(classObj instanceof Class) {
            objectClass = (Class)classObj;
        }
        else {
            try {
                objectClass = Class.forName(String.valueOf(classObj));
            }
            catch (ClassNotFoundException e) {
                throw new MarmaladeExecutionException("invalid bean class", e);
            }
        }
        
        try {
            this.target = objectClass.newInstance();
        }
        catch (InstantiationException e) {
            throw new MarmaladeExecutionException("error instantiating bean", e);
        }
        catch (IllegalAccessException e) {
            throw new MarmaladeExecutionException("error instantiating bean", e);
        }
        
        MarmaladeAttributes attributes = getAttributes();
        for (Iterator it = attributes.iterator(); it.hasNext();) {
            MarmaladeAttribute attribute = (MarmaladeAttribute) it.next();
            
            String attributeName = attribute.getName();
            if(CLASS_ATTRIBUTE.equals(attributeName) ||
               VAR_ATTRIBUTE.equals(attributeName)) 
            {
                //skip.
            }
            else
            {
                getExpressionEvaluator().assign(target, attributeName, attribute.getValue(context));
            }
        }
        
        context.setVariable(var, target);
    }
    
    protected void doReset() {
        this.target = null;
        super.doReset();
    }

    public Object getTarget() {
        return target;
    }
}
