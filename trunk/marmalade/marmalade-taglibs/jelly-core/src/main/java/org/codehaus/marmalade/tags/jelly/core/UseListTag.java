/* Created on Jun 23, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class UseListTag extends AbstractJellyMarmaladeTag {
    
    public static final String ITEMS_ATTRIBUTE = "items";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VAR_ATTRIBUTE = "var";

    public UseListTag(MarmaladeTagInfo tagInfo) {
        super(tagInfo);
    }

    protected void doExecute(MarmaladeExecutionContext context)
    throws MarmaladeExecutionException 
    {
        String var = (String)requireTagAttribute(VAR_ATTRIBUTE, String.class, context);
        Object result = requireTagAttribute(ITEMS_ATTRIBUTE, context);
        
        String className = (String)getAttributes().getValue(CLASS_ATTRIBUTE, String.class, context);
        
        Class collectionClass = null;
        if(className == null || className.length() < 1) {
            collectionClass = ArrayList.class;
        }
        else{
            try {
                collectionClass = Class.forName(className);
            }
            catch (ClassNotFoundException e) {
                throw new MarmaladeExecutionException("cannot load list class.", e);
            }
        }
        
        if(!List.class.isAssignableFrom(collectionClass)) {
            throw new MarmaladeExecutionException("specified class is not of type List");
        }
        
        try {
            List impl = (List)collectionClass.newInstance();
            
            if(result instanceof Collection) {
                impl.addAll((Collection)result);
            }
            else {
                impl.add(result);
            }
            
            context.setVariable(var, impl);
        }
        catch (InstantiationException e) {
            throw new MarmaladeExecutionException("cannot instantiate list", e);
        }
        catch (IllegalAccessException e) {
            throw new MarmaladeExecutionException("cannot instantiate list", e);
        }
    }

}
