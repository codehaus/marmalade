/*
 *
 * Copyright (c) 2004 John Dennis Casey
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */
/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class SetTag extends AbstractMarmaladeTag
{
    public static final String VAR_ATTRIBUTE = "var";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String PROPERTY_ATTRIBUTE = "property";
    public static final String TARGET_ATTRIBUTE = "target";

    public SetTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        Object value = getBody( context );

        if ( value == null )
        {
            value = requireTagAttribute( VALUE_ATTRIBUTE, context );
        }

        MarmaladeAttributes attributes = getAttributes(  );
        String var = ( String ) attributes.getValue( VAR_ATTRIBUTE,
                String.class, context );

        if ( ( var != null ) && ( var.length(  ) > 0 ) )
        {
            context.setVariable( var, value );
        }
        else
        {
            setObjectProperty( value, attributes, context );
        }
    }

    private void setObjectProperty( Object value,
        MarmaladeAttributes attributes, MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        Object target = requireTagAttribute( TARGET_ATTRIBUTE, context );
        String property = ( String ) requireTagAttribute( PROPERTY_ATTRIBUTE,
                String.class, context );

        ExpressionEvaluator el = getExpressionEvaluator(  );

        el.assign( target, property, value );
    }
}
