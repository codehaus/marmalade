
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
package org.codehaus.marmalade.parsetime;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.marmalade.metamodel.MarmaladeModelBuilderException;
import org.codehaus.marmalade.metamodel.MarmaladeTagBuilder;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.tagalog.ParserConfiguration;
import org.codehaus.tagalog.TagalogParseException;
import org.codehaus.tagalog.TagalogParser;
import org.codehaus.tagalog.sax.TagalogSAXParserFactory;
import org.xml.sax.SAXException;

/**
 * @author jdcasey
 */
public class ScriptParser
{
    private MarmaladeTaglibResolver marmaladeResolver;

    public ScriptParser( MarmaladeTaglibResolver marmaladeResolver )
    {
        this.marmaladeResolver = marmaladeResolver;
    }

    public MarmaladeScript parse( File input )
        throws MarmaladeParsetimeException, MarmaladeModelBuilderException
    {
        BufferedInputStream in = null;

        try
        {
            in = new BufferedInputStream( new FileInputStream( input ) );

            MarmaladeTagBuilder result = _parse( in, input.getAbsolutePath(  ) );

            // result is guaranteed not to be null, because MEE will be thrown from _parse() if so.
            return new MarmaladeScript( input.getAbsolutePath(  ),
                result.build(  ) );
        }
        catch ( FileNotFoundException e )
        {
            throw new MarmaladeScriptNotFoundException( input.getAbsolutePath(  ) );
        }
        finally
        {
            if ( in != null )
            {
                try
                {
                    in.close(  );
                }
                catch ( IOException e )
                {
                }
            }
        }
    }

    public MarmaladeScript parse( URL input )
        throws MarmaladeParsetimeException, MarmaladeModelBuilderException
    {
        BufferedInputStream in = null;

        try
        {
            in = new BufferedInputStream( input.openStream(  ) );

            MarmaladeTagBuilder result = _parse( in, input.toExternalForm(  ) );

            // result is guaranteed not to be null, because MEE will be thrown from _parse() if so.
            return new MarmaladeScript( input.toExternalForm(  ),
                result.build(  ) );
        }
        catch ( IOException e )
        {
            throw new MarmaladeScriptNotFoundException( input.toExternalForm(  ) );
        }
        finally
        {
            if ( in != null )
            {
                try
                {
                    in.close(  );
                }
                catch ( IOException e )
                {
                }
            }
        }
    }

    private MarmaladeTagBuilder _parse( InputStream in, String location )
        throws MarmaladeParsetimeException
    {
        MarmaladeTagBuilder result = null;

        try
        {
            ParserConfiguration config = new ParserConfiguration(  );

            config.addTagLibraryResolver( new TagalogTaglibResolver( 
                    TagalogTaglibResolver.DEFAULT_MARMALADE_TAGLIB_PREFIX,
                    marmaladeResolver ) );

            TagalogSAXParserFactory factory = new TagalogSAXParserFactory( config );
            TagalogParser parser = factory.createParser( in );

            result = ( MarmaladeTagBuilder ) parser.parse(  );
        }
        catch ( ParserConfigurationException e )
        {
            throw new MarmaladeParsetimeException( e );
        }
        catch ( SAXException e )
        {
            throw new MarmaladeParsetimeException( e );
        }
        catch ( TagalogParseException e )
        {
            throw new MarmaladeParsetimeException( e );
        }

        if ( result == null )
        {
            throw new EmptyScriptException( "Error parsing script at: "
                + location + ". Reason: resulting root tag was null." );
        }
        else
        {
            return result;
        }
    }
}
