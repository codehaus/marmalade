/* Created on Sep 20, 2004 */
package org.codehaus.marmalade.model;

import java.io.StringReader;

import org.codehaus.marmalade.metamodel.ModelBuilderException;
import org.codehaus.marmalade.metamodel.ScriptBuilder;
import org.codehaus.marmalade.parsing.DefaultParsingContext;
import org.codehaus.marmalade.parsing.MarmaladeParsetimeException;
import org.codehaus.marmalade.parsing.MarmaladeParsingContext;
import org.codehaus.marmalade.parsing.ScriptParser;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.core.CoreTagLibrary;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class DefaultTaglibTest
    extends TestCase
{

    public void testShouldUseDefaultTaglibToResolveScript() throws ModelBuilderException, MarmaladeParsetimeException, MarmaladeExecutionException {
        String script = "<set var=\"testvar\" value=\"testvalue\"/>";
        
        MarmaladeParsingContext ctx = new DefaultParsingContext();
        ctx.setDefaultTagLibrary(new CoreTagLibrary());
        
        StringReader reader = new StringReader(script);
        ctx.setInput(reader);
        ctx.setInputLocation("inline test script");
        
        ScriptBuilder builder = new ScriptParser().parse(ctx);
        
        MarmaladeScript mmld = builder.build();
        
        MarmaladeExecutionContext eCtx = new DefaultContext();
        
        mmld.execute(eCtx);
        
        assertEquals("testvalue", eCtx.getVariable("testvar", null));
    }
}
