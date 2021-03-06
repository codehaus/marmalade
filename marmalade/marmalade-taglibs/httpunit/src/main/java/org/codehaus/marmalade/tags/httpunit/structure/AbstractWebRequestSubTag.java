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

import com.meterware.httpunit.WebRequest;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class AbstractWebRequestSubTag extends AbstractWebConversationSubTag
    implements WebRequestSubTag
{
    public static final String REQUEST_ATTRIBUTE = "request";

    protected AbstractWebRequestSubTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    public WebRequest getRequest( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        MarmaladeAttributes attrs = getAttributes(  );

        WebRequest request = ( WebRequest ) attrs.getValue( REQUEST_ATTRIBUTE,
                WebRequest.class, context );

        if ( request == null )
        {
            WebRequestTag ancestor = ( WebRequestTag ) requireAncestor( WebRequestTag.class );

            request = ancestor.getRequest(  );
        }

        return request;
    }
}