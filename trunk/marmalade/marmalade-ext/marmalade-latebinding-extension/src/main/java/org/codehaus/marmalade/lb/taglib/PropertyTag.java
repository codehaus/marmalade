/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class PropertyTag
    extends AbstractValueTag
{
    public static final String NAME_ATTRIBUTE = "name";

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
        String name = (String) requireTagAttribute( NAME_ATTRIBUTE, String.class, context );

        Object value = extractValue( context );

        PropertyOwner parent = (PropertyOwner) requireParent( PropertyOwner.class );
        parent.addProperty( name, value );
    }
}