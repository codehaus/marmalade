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

import org.codehaus.marmalade.el.PassThroughExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.xmlpull.v1.XmlPullParserException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

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
        BufferedReader in = null;

        try {
            in = new BufferedReader( new InputStreamReader(input, "UTF-8"));
            ScriptBuilder result = _parse( in, location );

            return result;
        }
        catch (UnsupportedEncodingException e) {
            throw new MarmaladeParsetimeException(e);
        }
    }

    public ScriptBuilder parse( File input )
        throws MarmaladeParsetimeException, MarmaladeModelBuilderException
    {
        BufferedReader in = null;

        try
        {
            in = new BufferedReader( new FileReader(input));

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
        BufferedReader in = null;

        try
        {
            in = new BufferedReader( new InputStreamReader(input.openStream(  ), "UTF-8"));

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

    private ScriptBuilder _parse( Reader reader, String location )
        throws MarmaladeParsetimeException
    {
        ScriptBuilder result = null;

        try
        {
            MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver(MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN);
            MarmaladeParsingContext context = new DefaultParsingContext();
            context.setDefaultExpressionEvaluator(new PassThroughExpressionEvaluator());
            
            ScriptReader scriptReader = new ScriptReader();

            result = ( ScriptBuilder ) scriptReader.readScript(reader, location, resolver, context);
        } catch (XmlPullParserException e) {
            throw new MarmaladeParsetimeException(e);
        } catch (IOException e) {
            throw new MarmaladeParsetimeException(e);
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
