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

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.parsetime.MarmaladeModelBuilderException;
import org.codehaus.marmalade.parsetime.MarmaladeParsetimeException;
import org.codehaus.marmalade.parsetime.ScriptBuilder;
import org.codehaus.marmalade.parsetime.ScriptParser;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jelly.AbstractJellyMarmaladeTag;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringReader;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author jdcasey
 */
public class ParseTag extends AbstractJellyMarmaladeTag
{
    public static final String XML_READER_ATTRIBUTE = "XMLReader";
    public static final String JELLY_PARSER_ATTRIBUTE = "jellyParser";
    public static final String VAR_ATTRIBUTE = "var";
    public static final String TEXT_ATTRIBUTE = "text";

    public ParseTag(  )
    {
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        deprecateTagAttribute( XML_READER_ATTRIBUTE, context );
        deprecateTagAttribute( JELLY_PARSER_ATTRIBUTE, context );

        MarmaladeAttributes attributes = getAttributes(  );

        try
        {
            MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver( MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN );
            ScriptParser parser = new ScriptParser( resolver );

            String text = ( String ) attributes.getValue( TEXT_ATTRIBUTE,
                    context );

            if ( ( text == null ) || ( text.length(  ) < 1 ) )
            {
                text = getRawBody( context );
            }

            if ( ( text == null ) || ( text.length(  ) < 1 ) )
            {
                throw new MarmaladeExecutionException( 
                    "file or text attributes, or tag body must be specified and contain a jelly/marmalade script." );
            }
            else
            {
                StringBuffer preparedText = new StringBuffer(  );

                if ( !text.startsWith( "<?xml" ) )
                {
                    // Make this into a valid XML document.
                    preparedText.append( "<?xml version=\"1.0\"?>\n" );

                    // and ensure that it only has a single document element.
                    preparedText.append( 
                        "<zzz:jelly xmlns:zzz=\"jelly:core\">\n\n" ).append( text )
                                .append( "\n\n</zzz:jelly>" );
                }
                else
                {
                    // If it has an XML declaration, we assume everything is right with the world.
                    preparedText.append( text );
                }

                StringReader reader = new StringReader( preparedText.toString(  ) );

                MarmaladeTagInfo ti = getTagInfo(  );
                ScriptBuilder builder = parser.parse( reader,
                        "inline/internal script (file: " + ti.getSourceFile(  )
                        + ", line: " + ti.getSourceLine(  ) + ")" );

                MarmaladeScript script = builder.build();
                
                String var = ( String ) attributes.getValue( VAR_ATTRIBUTE,
                        String.class, context );

                if ( ( var == null ) || ( var.length(  ) < 1 ) )
                {
                    // if var attribute is missing, execute the script as an
                    // extension of this script.
                    script.execute( context );
                }
                else
                {
                    context.setVariable( var, script );
                }
            }
        }
        catch ( MarmaladeParsetimeException e )
        {
            throw new MarmaladeExecutionException( "Error parsing script", e );
        }
        catch ( MarmaladeModelBuilderException e )
        {
            throw new MarmaladeExecutionException( "Error parsing script", e );
        }
    }
}
