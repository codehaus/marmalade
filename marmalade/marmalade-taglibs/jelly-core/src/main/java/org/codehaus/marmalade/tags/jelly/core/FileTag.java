
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
/* Created on Apr 20, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.AbstractOutputTag;
import org.codehaus.marmalade.util.XMLUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author jdcasey
 */
public class FileTag extends AbstractOutputTag
{
    public static final String NAME_ATTRIBUTE = "name";
    public static final String VAR_ATTRIBUTE = "var";
    public static final String ENCODING_ATTRIBUTE = "encoding";
    public static final String ESCAPE_TEXT_ATTRIBUTE = "escapeText";
    public static final String OMIT_XML_DECL_ATTRIBUTE = "omitXmlDeclaration";
    public static final String OUTPUT_MODE_ATTRIBUTE = "outputMode";
    public static final String PRETTY_PRINT_ATTRIBUTE = "prettyPrint";
    private static final String OUTPUT_MODE_XML = "XML";
    private static final String OUTPUT_MODE_HTML = "HTML";

    public FileTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected void write( String message, MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        // Save the old output writer
        PrintWriter oldOut = context.getOutWriter(  );

        // Create a new output writer for capturing the child contents.
        StringWriter str = new StringWriter(  );
        PrintWriter out = new PrintWriter( str );

        context.setOutWriter( out );

        // Run the children through, capturing the output
        processChildren( context );

        // Restore the old output writer
        context.setOutWriter( oldOut );

        MarmaladeAttributes attrs = getAttributes(  );

        // Handle prettyPrint attribute
        Boolean prettyPrint = ( Boolean ) attrs.getValue( PRETTY_PRINT_ATTRIBUTE,
                Boolean.class, context );

        if ( prettyPrint != null )
        {
            context.getErrWriter(  ).println( "NOTE: Explicit pretty-printing is not currently supported." );
        }

        StringBuffer buffer = new StringBuffer(  );

        // Handle outputMode attribute
        String outputMode = ( String ) attrs.getValue( OUTPUT_MODE_ATTRIBUTE,
                String.class, context );

        if ( OUTPUT_MODE_XML.equals( outputMode ) )
        {
            Boolean omitXmlHeader = ( Boolean ) attrs.getValue( OMIT_XML_DECL_ATTRIBUTE,
                    Boolean.class, context, Boolean.FALSE );

            // Handle omitXmlHeader attribute
            if ( !omitXmlHeader.booleanValue(  ) )
            {
                buffer.append( "<?xml version=\"1.0\" encoding=\"" );

                String encoding = ( String ) attrs.getValue( ENCODING_ATTRIBUTE,
                        String.class, context, "UTF-8" );

                buffer.append( encoding ).append( "\"?>" );
            }
        }
        else if ( OUTPUT_MODE_HTML.equals( outputMode ) )
        {
            // do nothing
        }
        else
        {
            throw new MarmaladeExecutionException( "Invalid Output Mode: "
                + outputMode );
        }

        Boolean escapeText = ( Boolean ) attrs.getValue( ESCAPE_TEXT_ATTRIBUTE,
                Boolean.class, context, Boolean.FALSE );

        String content = str.getBuffer(  ).toString(  );

        // Handle escapeText attribute
        if ( escapeText.booleanValue(  ) )
        {
            content = XMLUtils.escapeXml( content );
        }

        Object file = attrs.getValue( NAME_ATTRIBUTE, context );

        if ( file == null )
        {
            String var = ( String ) requireTagAttribute( VAR_ATTRIBUTE,
                    String.class, context );

            // Handle name attribute
            context.setVariable( var, content );
        }

        // Handle name attribute
        else
        {
            BufferedWriter fileOut = null;

            try
            {
                if ( file instanceof File )
                {
                    fileOut = new BufferedWriter( new FileWriter( ( File ) file ) );
                }
                else
                {
                    fileOut = new BufferedWriter( new FileWriter( 
                                ( String ) file ) );
                }

                fileOut.write( content );
                fileOut.flush(  );
            }
            catch ( IOException e )
            {
                throw new MarmaladeExecutionException( 
                    "Error writing body content to file: " + file );
            }
            finally
            {
                if ( fileOut != null )
                {
                    try
                    {
                        fileOut.close(  );
                    }
                    catch ( IOException e )
                    {
                    }
                }
            }
        }
    }
}
