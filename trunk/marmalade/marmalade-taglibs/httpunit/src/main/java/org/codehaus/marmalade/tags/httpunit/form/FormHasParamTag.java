
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* Created on Apr 22, 2004 */
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