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

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.model.MarmaladeControlDefinitions;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.parsetime.DefaultParsingContext;
import org.codehaus.marmalade.parsetime.MarmaladeModelBuilderException;
import org.codehaus.marmalade.parsetime.MarmaladeParsetimeException;
import org.codehaus.marmalade.parsetime.MarmaladeParsingContext;
import org.codehaus.marmalade.parsetime.ScriptBuilder;
import org.codehaus.marmalade.parsetime.ScriptParser;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.util.RecordingReader;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class PassThroughTagTest extends TestCase
{
    public void testShouldCollapseOpenCloseElementWithEmptyBody() throws MarmaladeParsetimeException, MarmaladeModelBuilderException, MarmaladeExecutionException {
        String script = "<?xml version=\"1.0\"?><z:test xmlns:z=\"nothing:nothing\"></z:test>";
        String check = "<test xmlns=\"nothing:nothing\"/>";

        String result = parseAndExecute(script);
        
        assertTrue(result.indexOf(":test") > -1);
        assertTrue(result.indexOf("\"nothing:nothing\"") > -1);
    }
    
    public void testShouldReproduceEmptyTag() throws MarmaladeExecutionException, MarmaladeParsetimeException, MarmaladeModelBuilderException {
        String script = "<?xml version=\"1.0\"?><z:test attr1=\"one\" xmlns:z=\"nothing:nothing\"/>";

        String result = parseAndExecute(script);
        
        assertTrue(result.indexOf("attr1=\"one\"") > -1);
        assertTrue(result.indexOf(":test") > -1);
        assertTrue(result.indexOf("\"nothing:nothing\"") > -1);
    }

    public void testShouldReproduceTagWithExactBodyWhenPreserveWhitespaceNotSpecified() throws MarmaladeExecutionException, MarmaladeParsetimeException, MarmaladeModelBuilderException {
        String script = "<?xml version=\"1.0\"?><z:test attr1=\"one\" xmlns:z=\"nothing:nothing\">This is a \ntest.</z:test>";

        String result = parseAndExecute(script);
        
        System.out.println("Result: \'" + result + "\'");
        assertTrue("Attribute should be in result.", result.indexOf("attr1=\"one\"") > -1);
        assertTrue("Start element should be in result.", result.indexOf(":test") > -1);
        assertTrue("XMLNS should be in result.", result.indexOf("\"nothing:nothing\"") > -1);
        assertTrue("Trimmed body should be in result.", result.indexOf("This is a \ntest.") > -1);
    }

/* [JDC 7/1/2004]:
 * We can't test this without a robust expression evaluator...which means 
 * making a required dependency out of OGNL...otherwise marmalade-el-ognl will 
 * complete a cyclic dependency with marmalade-core WRT testing of the core...
 *  
 *   public void testShouldReproduceTagWithExactBodyWhenPreserveWhitespaceSpecified() throws MarmaladeExecutionException, MarmaladeParsetimeException, MarmaladeModelBuilderException {
        String script = "<?xml version=\"1.0\"?><z:test xmlns:marmalade=\"marmalade:ctl-opts\" xmlns:z=\"nothing:nothing\" attr1=\"one\" marmalade:preserve-whitespace=\"true\">This is a \ntest.</z:test>";

        String result = parseAndExecute(script);
        
        System.out.println("Result: \'" + result + "\'");
        assertTrue("Attribute should be in result.", result.indexOf("attr1=\"one\"") > -1);
        assertTrue("Start element should be in result.", result.indexOf(":test") > -1);
        assertTrue("XMLNS should be in result.", result.indexOf("\"nothing:nothing\"") > -1);
        assertTrue("marmalade:preserve-whitespace should be in result.", result.indexOf(MarmaladeControlDefinitions.MARMALADE_RESERVED_NS + ":" + MarmaladeControlDefinitions.PRESERVE_BODY_WHITESPACE_ATTRIBUTE) > -1);
        assertTrue("Untrimmed body should be in result.", result.indexOf("This is a \ntest.") > -1);
    }*/

    private String parseAndExecute(String script) throws MarmaladeExecutionException, MarmaladeParsetimeException, MarmaladeModelBuilderException {
        StringReader reader = new StringReader(script);
        MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver(MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN);
        String location = "test location";
        
        MarmaladeParsingContext pCtx = new DefaultParsingContext();
        pCtx.setTaglibResolver(resolver);
        pCtx.setInput(new RecordingReader(reader));
        pCtx.setInputLocation(location);
        
        ScriptParser parser = new ScriptParser();
        
        ScriptBuilder mmldBuilder = parser.parse(pCtx);
        
        MarmaladeScript mmld = mmldBuilder.build();
        
        StringWriter sWriter = new StringWriter();
        
        PrintWriter out = new PrintWriter(sWriter);
        
        DefaultContext ctx = new DefaultContext();
        ctx.setOutWriter(out);
        
        mmld.execute(ctx);
        
        return sWriter.getBuffer().toString();
    }

}
