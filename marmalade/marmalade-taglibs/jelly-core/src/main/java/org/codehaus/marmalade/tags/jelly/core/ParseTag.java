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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringReader;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author jdcasey
 */
public class ParseTag extends AbstractMarmaladeTag
{
    public static final String XML_READER_ATTRIBUTE = "XMLReader";
    public static final String JELLY_PARSER_ATTRIBUTE = "jellyParser";
    public static final String VAR_ATTRIBUTE = "var";
    public static final String TEXT_ATTRIBUTE = "text";

    public ParseTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        deprecateTagAttribute(XML_READER_ATTRIBUTE, context);
        deprecateTagAttribute(JELLY_PARSER_ATTRIBUTE, context);
        
        MarmaladeAttributes attributes = getAttributes();
        
        try
        {
            MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver( MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN );
            ScriptParser parser = new ScriptParser( resolver );
            
            String text = (String)attributes.getValue(TEXT_ATTRIBUTE, context);
            
            if(text == null || text.length() < 1) {
                text = getRawBody(context);
            }
            
            if(text == null || text.length() < 1) {
                throw new MarmaladeExecutionException("file or text attributes, or tag body must be specified and contain a jelly/marmalade script.");
            }
            else {
                StringBuffer preparedText = new StringBuffer();
                if(!text.startsWith("<?xml")) {
                    // Make this into a valid XML document.
                    preparedText.append("<?xml version=\"1.0\"?>\n");
                    
                    // and ensure that it only has a single document element.
                    preparedText.append("<zzz:jelly xmlns:zzz=\"jelly:core\">\n\n")
                                .append(text)
                                .append("\n\n</zzz:jelly>");
                }
                else {
                    // If it has an XML declaration, we assume everything is right with the world.
                    preparedText.append(text);
                }
                
                StringReader reader = new StringReader(preparedText.toString());
                
                MarmaladeTagInfo ti = getTagInfo();
                MarmaladeScript script = parser.parse(reader, "inline/internal script (file: " + ti.getSourceFile() + ", line: " + ti.getSourceLine() + ")");
                
                String var = ( String ) attributes.getValue( VAR_ATTRIBUTE,
                        String.class, context );
                
                if(var == null || var.length() < 1) {
                    // if var attribute is missing, execute the script as an
                    // extension of this script.
                    script.execute(context);
                }
                else {
                    context.setVariable( var, script );
                }
            }
        }
        catch ( MarmaladeParsetimeException e )
        {
            throw new MarmaladeExecutionException( 
                "Error parsing script", e );
        }
        catch ( MarmaladeModelBuilderException e )
        {
            throw new MarmaladeExecutionException( 
                "Error parsing script", e );
        }
    }

}
