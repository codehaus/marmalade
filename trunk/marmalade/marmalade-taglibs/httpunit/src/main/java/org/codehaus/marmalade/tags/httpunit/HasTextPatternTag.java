
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
package org.codehaus.marmalade.tags.httpunit;

import com.meterware.httpunit.WebResponse;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.io.IOException;

/**
 * @author jdcasey
 */
public class HasTextPatternTag extends AbstractAssertionTag
{
    public static final String WITH_PATTERN_ATTRIBUTE = "withPattern";

    public HasTextPatternTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected boolean test( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        String value = ( String ) requireTagAttribute( WITH_PATTERN_ATTRIBUTE,
                String.class, context );
        WebResponse response = getResponse( context );

        try
        {
            return response.getText(  ).matches( value );
        }
        catch ( IOException e )
        {
            throw new MarmaladeExecutionException( "Error reading web response.",
                e );
        }
    }
}
