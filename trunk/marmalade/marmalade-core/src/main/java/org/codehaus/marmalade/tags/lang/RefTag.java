package org.codehaus.marmalade.tags.lang;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 *
 * Created on Feb 4, 2005
 */
public class RefTag
    extends AbstractMarmaladeTag
{
    
    public static final String REF_ATTR = "ref";

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        String ref = (String)requireTagAttribute(REF_ATTR, String.class, context);
        Object referent = context.getVariable(ref, getExpressionEvaluator());
        
        ValueTagParent parent = (ValueTagParent)requireParent(ValueTagParent.class);
        parent.setValue(referent);
    }
}
