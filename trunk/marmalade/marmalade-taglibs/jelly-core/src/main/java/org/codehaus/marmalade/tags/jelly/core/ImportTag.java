
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

/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeModelBuilderException;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.parsetime.MarmaladeParsetimeException;
import org.codehaus.marmalade.parsetime.ScriptParser;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author jdcasey
 */
public class ImportTag extends AbstractMarmaladeTag
{
    public static final String URL_ATTRIBUTE = "url";
    public static final String VAR_ATTRIBUTE = "var";
    public static final String PARSE_ONLY_ATTRIBUTE = "parse-only";

    public ImportTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        Object location = requireTagAttribute( URL_ATTRIBUTE, Object.class,
                context );
        URL resource = null;

        if ( location instanceof URL )
        {
            resource = ( URL ) location;
        }
        else if ( location instanceof String )
        {
            try
            {
                resource = new URL( ( String ) location );
            }
            catch ( MalformedURLException e )
            {
                throw new MarmaladeExecutionException( "Error parsing url into java.net.URL.",
                    e );
            }
        }
        else if ( location instanceof File )
        {
            try
            {
                resource = ( ( File ) location ).toURL(  );
            }
            catch ( MalformedURLException e )
            {
                throw new MarmaladeExecutionException( "Error parsing url into java.net.URL.",
                    e );
            }
        }
        else
        {
            throw new MarmaladeExecutionException( 
                "url attribute must be String, java.net.URL, or java.io.File type." );
        }

        try
        {
            MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver( MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN );
            ScriptParser parser = new ScriptParser( resolver );
            MarmaladeScript script = parser.parse( resource );
            MarmaladeAttributes attrs = getAttributes(  );
            
            Boolean parseOnly = ( Boolean ) attrs.getValue( PARSE_ONLY_ATTRIBUTE, Boolean.class, context, Boolean.FALSE );
            boolean shouldExec = ( parseOnly == null ) ? ( true )
                                                       : ( !parseOnly.booleanValue(  ) );

            if ( shouldExec )
            {
                script.execute( context );

                String var = ( String ) attrs.getValue( VAR_ATTRIBUTE,
                        String.class, context );

                if ( ( var != null ) && ( var.length(  ) > 0 ) )
                {
                    context.setVariable( var, script );
                }
            }
            else
            {
                String var = ( String ) requireTagAttribute( VAR_ATTRIBUTE,
                        String.class, context );

                if ( ( var != null ) && ( var.length(  ) > 0 ) )
                {
                    context.setVariable( var, script );
                }
            }
        }
        catch ( MarmaladeParsetimeException e )
        {
            throw new MarmaladeExecutionException( 
                "Error parsing script from: " + resource.toExternalForm(  ), e );
        }
        catch ( MarmaladeModelBuilderException e )
        {
            throw new MarmaladeExecutionException( 
                "Error parsing script from: " + resource.toExternalForm(  ), e );
        }
    }
}
