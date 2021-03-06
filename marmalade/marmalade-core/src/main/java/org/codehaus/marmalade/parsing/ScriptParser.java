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
package org.codehaus.marmalade.parsing;

import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.metamodel.ScriptBuilder;
import org.codehaus.marmalade.monitor.log.MarmaladeLog;
import org.codehaus.marmalade.reader.ScriptReadException;
import org.codehaus.marmalade.reader.ScriptReader;
import org.codehaus.marmalade.reader.xml.Xpp3ScriptReader;

import java.io.IOException;

/**
 * @author jdcasey
 */
public class ScriptParser
{
    private final ScriptReader scriptReader;
    private MarmaladeLog log;

    public ScriptParser()
    {
        this.scriptReader = new Xpp3ScriptReader();
    }
    
    public ScriptParser(ScriptReader scriptReader)
    {
        this.scriptReader = scriptReader;
    }
    
    public ScriptBuilder parse( MarmaladeParsingContext context ) throws MarmaladeParsetimeException
    {
        ScriptBuilder result = null;

        String location = context.getInputLocation();

        try
        {
            MarmaladeTaglibResolver resolver = context.getTaglibResolver();

            Xpp3ScriptReader scriptReader = new Xpp3ScriptReader();

            result = (ScriptBuilder) scriptReader.readScript( context );
        }
        catch ( ScriptReadException e )
        {
            throw new MarmaladeParsetimeException( e );
        }
        catch ( IOException e )
        {
            throw new MarmaladeParsetimeException( e );
        }

        if ( result == null )
        {
            throw new EmptyScriptException( "Error parsing script at: " + location
                + ". Reason: resulting root tag was null." );
        }
        else
        {
            return result;
        }
    }

    public void setLog( MarmaladeLog log )
    {
        if(log != null)
        {
            this.log = log;
        }
    }
}