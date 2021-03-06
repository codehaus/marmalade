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

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author jdcasey
 */
public abstract class AbstractWebRequestTag
    extends AbstractWebConversationSubTag implements HeaderParent,
        WebRequestTag
{
    public static final String VAR_ATTRIBUTE = "var";
    public static final String URL_ATTRIBUTE = "url";
    public static final String BASE_URL_ATTRIBUTE = "baseUrl";
    public static final String TARGET_ATTRIBUTE = "target";
    private WebRequest request;

    protected AbstractWebRequestTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        MarmaladeAttributes attrs = getAttributes(  );

        String url = ( String ) requireTagAttribute( URL_ATTRIBUTE,
                String.class, context );
        
        String target = ( String ) attrs.getValue( TARGET_ATTRIBUTE,
                String.class, context );
        
        Object base = attrs.getValue( BASE_URL_ATTRIBUTE, context );
        URL baseUrl = null;

        if ( base != null )
        {
            if ( base instanceof String )
            {
                try
                {
                    baseUrl = new URL( ( String ) base );
                }
                catch ( MalformedURLException e )
                {
                    throw new MarmaladeExecutionException( "Error instantiating base URL.",
                        e );
                }
            }
            else
            {
                baseUrl = ( URL ) base;
            }
        }

        request = createRequest( baseUrl, url, target );

        String var = ( String ) attrs.getValue( VAR_ATTRIBUTE, String.class,
                context );

        if ( ( var != null ) && ( var.length(  ) > 0 ) )
        {
            context.setVariable( var, request );
        }
    }

    public WebRequest getRequest(  )
    {
        return request;
    }

    public void setHeader( String name, String value )
    {
        request.setHeaderField( name, value );
    }

    public void setParameter( String name, String value )
    {
        request.setParameter( name, value );
    }

    protected abstract WebRequest createRequest( URL baseUrl, String url,
        String target ) throws MarmaladeExecutionException;

    protected void doReset(  )
    {
        this.request = null;
        super.doReset(  );
    }
}