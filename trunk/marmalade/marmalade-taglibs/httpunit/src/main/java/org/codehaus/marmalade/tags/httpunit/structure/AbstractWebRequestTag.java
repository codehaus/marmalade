
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

/* Created on Apr 21, 2004 */
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
