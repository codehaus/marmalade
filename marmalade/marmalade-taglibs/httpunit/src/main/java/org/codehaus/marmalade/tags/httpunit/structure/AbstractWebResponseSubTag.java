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



* Created on Apr 21, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import com.meterware.httpunit.WebResponse;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class AbstractWebResponseSubTag extends AbstractWebRequestSubTag 
    implements WebResponseSubTag
{
    public static final String RESPONSE_ATTRIBUTE = "response";

    protected AbstractWebResponseSubTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    public WebResponse getResponse( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        MarmaladeAttributes attrs = getAttributes(  );

        WebResponse response = ( WebResponse ) attrs.getValue( RESPONSE_ATTRIBUTE,
                WebResponse.class, context );

        if ( response == null )
        {
            WebResponseTag ancestor = ( WebResponseTag ) requireAncestor( WebResponseTag.class );

            response = ancestor.getResponse(  );
        }

        return response;
    }
}