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
/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.model.MarmaladeAttribute;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.parsetime.MarmaladeModelBuilderException;
import org.codehaus.marmalade.parsetime.MarmaladeParsetimeException;
import org.codehaus.marmalade.parsetime.ScriptBuilder;
import org.codehaus.marmalade.parsetime.ScriptParser;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.AbstractConditionalTag;
import org.codehaus.marmalade.tags.jelly.AbstractJellyConditionalTag;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author jdcasey
 */
public class IncludeTag extends AbstractJellyConditionalTag
{
    public static final String URI_ATTRIBUTE = "uri";
    public static final String FILE_ATTRIBUTE = "file";
    public static final String EXPORT_ATTRIBUTE = "export";
    public static final String INHERIT_ATTRIBUTE = "inherit";

    public IncludeTag(  )
    {
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        if ( conditionMatches( context ) )
        {
            boolean usingBody = false;

            MarmaladeAttributes attributes = getAttributes(  );

            // Retrieve the inclusion source. Check for a file first.
            Object sourceObj = attributes.getValue( FILE_ATTRIBUTE, context );

            // No file? We'll look for a URL.
            if ( sourceObj == null )
            {
                sourceObj = attributes.getValue( URI_ATTRIBUTE, context );
            }

            // No file? No URL? We'll try the body. 
            if ( sourceObj == null )
            {
                sourceObj = getRawBody( context );
                usingBody = true;
            }

            // We have no input!
            if ( sourceObj == null )
            {
                throw new MarmaladeExecutionException( 
                    "file or uri attribute, or tag body must be specified and in the form of a valid jelly/marmalade script." );
            }

            // Now figure out how to get an input stream to this source
            // Then, parse the script.
            MarmaladeScript script = parseScript( sourceObj, usingBody );

            // Figure out what context this script should have, and 
            // execute it.
            MarmaladeExecutionContext subCtx = null;
            Boolean inherit = ( Boolean ) attributes.getValue( INHERIT_ATTRIBUTE,
                    Boolean.class, context, Boolean.FALSE );

            if ( inherit.booleanValue(  ) )
            {
                context.newScope(  );
                subCtx = context;
            }
            else
            {
                subCtx = new DefaultContext(  );
            }

            // Perform actual execution.
            script.execute( subCtx );

            // If we need to export the results of execution, do so; otherwise, 
            // if we're using an inherited context, clean up.
            Boolean export = ( Boolean ) attributes.getValue( EXPORT_ATTRIBUTE,
                    Boolean.class, context, Boolean.FALSE );

            if ( inherit.booleanValue(  ) )
            {
                context.lastScope( export.booleanValue(  ) );
            }
            else
            {
                context.importContext( subCtx );
            }
        }
    }

    private MarmaladeScript parseScript( Object sourceObj, boolean usingBody )
        throws MarmaladeExecutionException
    {
        MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver( MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN );
        ScriptParser parser = new ScriptParser( resolver );

        MarmaladeScript script = null;

        if ( sourceObj instanceof File )
        {
            script = parseAsFile( ( File ) sourceObj, parser );
        }
        else if ( sourceObj instanceof URL )
        {
            URL sourceUrl = ( URL ) sourceObj;

            script = parseAsURL( sourceUrl, parser );
        }
        else if ( sourceObj instanceof String )
        {
            String sourceStr = ( String ) sourceObj;

            if ( usingBody )
            {
                script = parseAsInlineString( sourceStr, parser );
            }
            else
            {
                URL sourceURL = null;

                try
                {
                    sourceURL = new URL( sourceStr );
                    script = parseAsURL( sourceURL, parser );
                }
                catch ( MalformedURLException e )
                {
                    throw new MarmaladeExecutionException( 
                        "error constructing URL to read from: " + sourceStr, e );
                }
            }
        }

        return script;
    }

    private MarmaladeScript parseAsInlineString( String sourceStr,
        ScriptParser parser )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = getTagInfo(  );
        String location = "included script (file: " + ti.getSourceFile(  )
            + ", line: " + ti.getSourceLine(  ) + ")";

        StringBuffer buffer = new StringBuffer(  );

        if ( !sourceStr.startsWith( "<?xml" ) )
        {
            buffer.append( "<?xml version=\"1.0\"?>\n" )
                  .append( "<zzz:jelly xmlns:zzz=\"jelly:core\">\n" )
                  .append( sourceStr ).append( "</zzz:jelly>" );
        }
        else
        {
            buffer.append( sourceStr );
        }

        StringReader reader = new StringReader( buffer.toString(  ) );

        try
        {
            ScriptBuilder builder = parser.parse( reader, location );
            return builder.build();
        }
        catch ( MarmaladeParsetimeException e )
        {
            throw new MarmaladeExecutionException( 
                "error parsing included script from: " + location, e );
        }
        catch ( MarmaladeModelBuilderException e )
        {
            throw new MarmaladeExecutionException( 
                "error building included script from: " + location, e );
        }
    }

    private MarmaladeScript parseAsFile( File sourceFile, ScriptParser parser )
        throws MarmaladeExecutionException
    {
        try
        {
            ScriptBuilder builder = parser.parse( sourceFile );
            return builder.build();
        }
        catch ( MarmaladeParsetimeException e )
        {
            throw new MarmaladeExecutionException( 
                "error parsing included script from: "
                + sourceFile.getAbsolutePath(  ), e );
        }
        catch ( MarmaladeModelBuilderException e )
        {
            throw new MarmaladeExecutionException( 
                "error building included script from: "
                + sourceFile.getAbsolutePath(  ), e );
        }
    }

    private MarmaladeScript parseAsURL( URL sourceUrl, ScriptParser parser )
        throws MarmaladeExecutionException
    {
        try
        {
            ScriptBuilder builder = parser.parse( sourceUrl );
            return builder.build();
        }
        catch ( MarmaladeParsetimeException e )
        {
            throw new MarmaladeExecutionException( 
                "error parsing included script from: "
                + sourceUrl.toExternalForm(  ), e );
        }
        catch ( MarmaladeModelBuilderException e )
        {
            throw new MarmaladeExecutionException( 
                "error building included script from: "
                + sourceUrl.toExternalForm(  ), e );
        }
    }
}
