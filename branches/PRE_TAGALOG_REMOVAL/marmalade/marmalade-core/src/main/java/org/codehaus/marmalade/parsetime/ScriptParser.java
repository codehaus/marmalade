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
package org.codehaus.marmalade.parsetime;

import org.codehaus.marmalade.metamodel.MarmaladeModelBuilderException;
import org.codehaus.marmalade.metamodel.MarmaladeTagBuilder;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.metamodel.ScriptBuilder;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.tagalog.ParserConfiguration;
import org.codehaus.tagalog.TagalogParseException;
import org.codehaus.tagalog.TagalogParser;
import org.codehaus.tagalog.sax.TagalogSAXParserFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.net.URL;

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

    public ScriptBuilder parse( Reader input, String location )
        throws MarmaladeParsetimeException, MarmaladeModelBuilderException
    {
        ScriptBuilder result = _parse( input, location );

        return result;
    }

    public ScriptBuilder parse( InputStream input, String location )
        throws MarmaladeParsetimeException, MarmaladeModelBuilderException
    {
        ScriptBuilder result = _parse( input, location );

        return result;
    }

    public ScriptBuilder parse( File input )
        throws MarmaladeParsetimeException, MarmaladeModelBuilderException
    {
        BufferedInputStream in = null;

        try
        {
            in = new BufferedInputStream( new FileInputStream( input ) );

            ScriptBuilder result = _parse( in, input.getAbsolutePath(  ) );

            return result;
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

    public ScriptBuilder parse( URL input )
        throws MarmaladeParsetimeException, MarmaladeModelBuilderException
    {
        BufferedInputStream in = null;

        try
        {
            in = new BufferedInputStream( input.openStream(  ) );

            ScriptBuilder result = _parse( in, input.toExternalForm(  ) );

            return result;
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

    private ScriptBuilder _parse( InputStream in, String location )
        throws MarmaladeParsetimeException
    {
        ScriptBuilder result = null;

        try
        {
            ParserConfiguration config = new ParserConfiguration( new TagalogTaglibResolver( 
                        TagalogTaglibResolver.DEFAULT_MARMALADE_TAGLIB_PREFIX,
                        marmaladeResolver, true ) );

            TagalogSAXParserFactory factory = new TagalogSAXParserFactory( config );

            TagalogParser parser = factory.createParser( in );

            MarmaladeTagBuilder rootBuilder = ( MarmaladeTagBuilder ) parser.parse(  );
            result = new ScriptBuilder(location, rootBuilder);
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

    private ScriptBuilder _parse( Reader in, String location )
        throws MarmaladeParsetimeException
    {
        ScriptBuilder result = null;

        try
        {
            ParserConfiguration config = new ParserConfiguration( new TagalogTaglibResolver( 
                        TagalogTaglibResolver.DEFAULT_MARMALADE_TAGLIB_PREFIX,
                        marmaladeResolver, true ) );

            TagalogSAXParserFactory factory = new TagalogSAXParserFactory( config );
            TagalogParser parser = factory.createParser( in );

            MarmaladeTagBuilder rootBuilder = ( MarmaladeTagBuilder ) parser.parse(  );
            result = new ScriptBuilder(location, rootBuilder);
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
