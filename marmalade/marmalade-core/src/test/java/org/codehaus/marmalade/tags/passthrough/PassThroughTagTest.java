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
/* Created on Jun 26, 2004 */
package org.codehaus.marmalade.tags.passthrough;

import junit.framework.TestCase;

import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.metamodel.ModelBuilderException;
import org.codehaus.marmalade.metamodel.ScriptBuilder;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.parsing.DefaultParsingContext;
import org.codehaus.marmalade.parsing.MarmaladeParsetimeException;
import org.codehaus.marmalade.parsing.MarmaladeParsingContext;
import org.codehaus.marmalade.parsing.ScriptParser;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.util.RecordingReader;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author jdcasey
 */
public class PassThroughTagTest
    extends TestCase
{
    public void testShouldCollapseOpenCloseElementWithEmptyBody() throws MarmaladeParsetimeException,
        ModelBuilderException, MarmaladeExecutionException
    {
        String script = "<?xml version=\"1.0\"?><z:test xmlns:z=\"nothing:nothing\"></z:test>";
        String check = "<test xmlns=\"nothing:nothing\"/>";

        String result = parseAndExecute( script );

        assertTrue( result.indexOf( ":test" ) > -1 );
        assertTrue( result.indexOf( "\"nothing:nothing\"" ) > -1 );
    }

    public void testShouldReproduceEmptyTag() throws MarmaladeExecutionException, MarmaladeParsetimeException,
        ModelBuilderException
    {
        String script = "<?xml version=\"1.0\"?><z:test attr1=\"one\" xmlns:z=\"nothing:nothing\"/>";

        String result = parseAndExecute( script );

        assertTrue( result.indexOf( "attr1=\"one\"" ) > -1 );
        assertTrue( result.indexOf( ":test" ) > -1 );
        assertTrue( result.indexOf( "\"nothing:nothing\"" ) > -1 );
    }

    public void testShouldReproduceTagWithExactBodyWhenPreserveWhitespaceNotSpecified()
        throws MarmaladeExecutionException, MarmaladeParsetimeException, ModelBuilderException
    {
        String script = "<?xml version=\"1.0\"?><z:test attr1=\"one\" xmlns:z=\"nothing:nothing\">This is a \ntest.</z:test>";

        String result = parseAndExecute( script );

        System.out.println( "Result: \'" + result + "\'" );
        assertTrue( "Attribute should be in result.", result.indexOf( "attr1=\"one\"" ) > -1 );
        assertTrue( "Start element should be in result.", result.indexOf( ":test" ) > -1 );
        assertTrue( "XMLNS should be in result.", result.indexOf( "\"nothing:nothing\"" ) > -1 );
        assertTrue( "Trimmed body should be in result.", result.indexOf( "This is a \ntest." ) > -1 );
    }

    public void testShouldLeaveElementPrefixOffWhenNotSpecified() throws MarmaladeExecutionException,
        MarmaladeParsetimeException, ModelBuilderException
    {
        String script = "<?xml version=\"1.0\"?><test attr1=\"one\" xmlns=\"nothing:nothing\" />";

        String result = parseAndExecute( script );

        System.out.println( "Result: \'" + result + "\'" );
        assertTrue( "Attribute should be in result.", result.indexOf( "attr1=\"one\"" ) > -1 );
        assertTrue( "Start element should be in result.", result.indexOf( "<test" ) > -1 );
        assertTrue( "XMLNS should be in result.", result.indexOf( "xmlns=\"nothing:nothing\"" ) > -1 );
    }

    public void testShouldLeaveElementPrefixOffWhenNotSpecifiedAndXMLNSNotSpecified()
        throws MarmaladeExecutionException, MarmaladeParsetimeException, ModelBuilderException
    {
        String script = "<?xml version=\"1.0\"?><test attr1=\"one\" />";

        String result = parseAndExecute( script );

        System.out.println( "Result: \'" + result + "\'" );
        assertTrue( "Attribute should be in result.", result.indexOf( "attr1=\"one\"" ) > -1 );
        assertTrue( "Start element should be in result.", result.indexOf( "<test" ) > -1 );
        assertTrue( "XMLNS should NOT be in result.", result.indexOf( "xmlns=\"nothing:nothing\"" ) < 0 );
    }

    /*
     * [JDC 7/1/2004]: We can't test this without a robust expression
     * evaluator...which means making a required dependency out of
     * OGNL...otherwise marmalade-el-ognl will complete a cyclic dependency with
     * marmalade-core WRT testing of the core... public void
     * testShouldReproduceTagWithExactBodyWhenPreserveWhitespaceSpecified()
     * throws MarmaladeExecutionException, MarmaladeParsetimeException,
     * MarmaladeParsetimeException { String script = " <?xml version=\"1.0\"?>
     * <z:test xmlns:marmalade=\"marmalade:ctl-opts\"
     * xmlns:z=\"nothing:nothing\" attr1=\"one\"
     * marmalade:preserve-whitespace=\"true\">This is a \ntest. </z:test>";
     * String result = parseAndExecute(script); System.out.println("Result: \'" +
     * result + "\'"); assertTrue("Attribute should be in result.",
     * result.indexOf("attr1=\"one\"") > -1); assertTrue("Start element should
     * be in result.", result.indexOf(":test") > -1); assertTrue("XMLNS should
     * be in result.", result.indexOf("\"nothing:nothing\"") > -1);
     * assertTrue("marmalade:preserve-whitespace should be in result.",
     * result.indexOf(MarmaladeControlDefinitions.MARMALADE_RESERVED_NS + ":" +
     * MarmaladeControlDefinitions.PRESERVE_BODY_WHITESPACE_ATTRIBUTE) > -1);
     * assertTrue("Untrimmed body should be in result.", result.indexOf("This is
     * a \ntest.") > -1); }
     */
    private String parseAndExecute( String script ) throws MarmaladeExecutionException, MarmaladeParsetimeException,
        ModelBuilderException
    {
        StringReader reader = new StringReader( script );
        String location = "test location";

        MarmaladeParsingContext pCtx = new DefaultParsingContext();

        pCtx.addTaglibDefinitionStrategies( MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN );
        pCtx.setInput( new RecordingReader( reader ) );
        pCtx.setInputLocation( location );

        ScriptParser parser = new ScriptParser();

        ScriptBuilder mmldBuilder = parser.parse( pCtx );

        MarmaladeScript mmld = mmldBuilder.build();

        StringWriter sWriter = new StringWriter();

        PrintWriter out = new PrintWriter( sWriter );

        DefaultContext ctx = new DefaultContext();

        ctx.setOutWriter( out );

        mmld.execute( ctx );

        return sWriter.getBuffer().toString();
    }
}