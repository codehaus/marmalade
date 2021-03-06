/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class MethodParamTag
    extends AbstractValueTag
{

    protected boolean alwaysProcessBody()
    {
        return false;
    }

    protected boolean alwaysProcessChildren()
    {
        return false;
    }

    protected void doExecute( MarmaladeExecutionContext context ) throws MarmaladeExecutionException
    {
        Object value = extractValue( context );

        MethodParamOwner parent = (MethodParamOwner) requireParent( MethodParamOwner.class );
        parent.addMethodParameter( value );
    }
}