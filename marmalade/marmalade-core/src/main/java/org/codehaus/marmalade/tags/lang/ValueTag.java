package org.codehaus.marmalade.tags.lang;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 *
 * Created on Feb 4, 2005
 */
public class ValueTag
    extends AbstractMarmaladeTag
{
    
    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        Object value = getBody(context);
        ValueTagParent parent = (ValueTagParent) requireParent(ValueTagParent.class);
        parent.setValue(value);
    }
}
