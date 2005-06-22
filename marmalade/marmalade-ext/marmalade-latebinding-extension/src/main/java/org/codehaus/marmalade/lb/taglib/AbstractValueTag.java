/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MissingAttributeException;

/**
 * @author jdcasey
 */
public abstract class AbstractValueTag
    extends AbstractMarmaladeTag
{

    public static final String VALUE_ATTRIBUTE = "value";

    protected Object extractValue( MarmaladeExecutionContext context ) throws MissingAttributeException,
        ExpressionEvaluationException
    {
        Object value = getAttributes().getValue( VALUE_ATTRIBUTE, Object.class, context );
        if ( value == null )
        {
            value = getBody( context );
        }

        return value;
    }

}