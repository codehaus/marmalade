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
import org.codehaus.marmalade.tags.jelly.AbstractJellyMarmaladeTag;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author jdcasey
 */
public class ImportTag extends AbstractJellyMarmaladeTag
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

            Boolean parseOnly = ( Boolean ) attrs.getValue( PARSE_ONLY_ATTRIBUTE,
                    Boolean.class, context, Boolean.FALSE );
            boolean shouldExec = ( parseOnly == null ) ? ( true )
                                                       : ( !parseOnly
                .booleanValue(  ) );

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
