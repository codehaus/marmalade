
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
