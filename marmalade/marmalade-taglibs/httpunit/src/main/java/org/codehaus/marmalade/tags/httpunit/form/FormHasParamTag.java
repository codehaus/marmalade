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



* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.form;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class FormHasParamTag extends AbstractFormSubAssertionTag
{
    public static final String WITH_NAME_ATTRIBUTE = "withName";
    public static final String WITH_VALUE_ATTRIBUTE = "withValue";

    public FormHasParamTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected boolean test( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        String name = ( String ) requireTagAttribute( WITH_NAME_ATTRIBUTE,
                String.class, context );

        String[] values = getForm( context ).getParameterValues( name );

        boolean result = false;
        String testVal = ( String ) getAttributes(  ).getValue( WITH_VALUE_ATTRIBUTE,
                String.class, context );

        if ( ( testVal == null ) || ( testVal.length(  ) < 1 ) )
        {
            result = ( values != null ) && ( values.length > 0 );
        }
        else
        {
            if ( ( values != null ) && ( values.length > 0 ) )
            {
                for ( int i = 0; i < values.length; i++ )
                {
                    String value = values[i];

                    if ( value.equals( testVal ) )
                    {
                        result = true;

                        break;
                    }
                }
            }
        }

        return result;
    }
}