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

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.xml.sax.SAXException;

import java.io.IOException;

import java.net.MalformedURLException;

/**
 * @author jdcasey
 */
public class DefaultWebResponseTag extends AbstractWebRequestSubTag
    implements WebResponseTag
{
    public static final String VAR_ATTRIBUTE = "var";
    private WebResponse response;

    public DefaultWebResponseTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    public WebResponse getResponse(  )
    {
        return response;
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        WebConversation conversation = getConversation( context );
        WebRequest request = getRequest( context );

        try
        {
            response = conversation.getResponse( request );

            String var = ( String ) getAttributes(  ).getValue( VAR_ATTRIBUTE,
                    String.class, context );

            if ( ( var != null ) && ( var.length(  ) > 0 ) )
            {
                context.setVariable( var, response );
            }
        }
        catch ( MalformedURLException e )
        {
            throw new MarmaladeExecutionException( "Error sending request.", e );
        }
        catch ( IOException e )
        {
            throw new MarmaladeExecutionException( "Error executing web request.",
                e );
        }
        catch ( SAXException e )
        {
            throw new MarmaladeExecutionException( "Error parsing response.", e );
        }
    }

    protected void doReset(  )
    {
        this.response = null;
        super.doReset(  );
    }
}