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

public class SetPropertiesTag extends AbstractJellyMarmaladeTag {
    
    public static final String OBJECT_ATTRIBUTE = "object";

    public SetPropertiesTag(MarmaladeTagInfo tagInfo) {
        super(tagInfo);
    }

    protected void doExecute(MarmaladeExecutionContext context)
    throws MarmaladeExecutionException 
    {
        MarmaladeAttributes attributes = getAttributes();
        
        Object target = attributes.getValue(OBJECT_ATTRIBUTE, context);
        if(target == null) {
            TargetObjectOwner owner = (TargetObjectOwner)requireAncestor(TargetObjectOwner.class);
            target = owner.getTarget();
        }
        
        for (Iterator it = attributes.iterator(); it.hasNext();) {
            MarmaladeAttribute attribute = (MarmaladeAttribute) it.next();
            
            String attributeName = attribute.getName();
            if(OBJECT_ATTRIBUTE.equals(attributeName))
            {
                //skip.
            }
            else
            {
                getExpressionEvaluator().assign(target, attributeName, attribute.getValue(context));
            }
        }
    }
}
