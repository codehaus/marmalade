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
/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.parsetime;

import junit.framework.TestCase;

import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.util.RecordingReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author jdcasey
 */
public class ScriptParserTest extends TestCase
{
    private static final String MESSAGE = "This is a test";
    private static final String TEST_PARSE_CONTENT =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n"
        + "<testTag xmlns=\"marmalade:org.codehaus.marmalade.tags.TestParseTaglib\" value=\""
        + MESSAGE + "\"/>";

    public void testParse(  )
        throws IOException, MarmaladeExecutionException, 
            MarmaladeParsetimeException, MarmaladeModelBuilderException
    {
        File f = File.createTempFile( "parse-test-", ".mmld" );
        PrintWriter fout = new PrintWriter( new FileWriter( f ) );

        fout.println( TEST_PARSE_CONTENT );
        fout.flush(  );
        fout.close(  );

        MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver( MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN );

        MarmaladeParsingContext pCtx = new DefaultParsingContext(  );

        pCtx.setTaglibResolver( resolver );

        ScriptBuilder scriptBuilder = null;
        BufferedReader input = null;

        try
        {
            input = new BufferedReader( new FileReader( f ) );

            pCtx.setInput( new RecordingReader( input ) );
            pCtx.setInputLocation( f.getAbsolutePath(  ) );

            ScriptParser parser = new ScriptParser(  );

            scriptBuilder = parser.parse( pCtx );
        }
        finally
        {
            if ( input != null )
            {
                try
                {
                    input.close(  );
                }
                catch ( IOException e )
                {
                }
            }
        }

        f.delete(  );

        assertNotNull( "Parsed script should not be null", scriptBuilder );

        DefaultContext ctx = new DefaultContext(  );
        StringWriter out = new StringWriter(  );

        ctx.setOutWriter( new PrintWriter( out ) );

        scriptBuilder.build(  ).execute( ctx );

        assertEquals( "Messages should equal.", MESSAGE,
            out.getBuffer(  ).toString(  ) );
    }
}
