/* Created on Jul 13, 2004 */
package org.codehaus.marmalade.compat.jelly;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.el.commonsEl.CommonsElExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.parsetime.DefaultParsingContext;
import org.codehaus.marmalade.parsetime.MarmaladeModelBuilderException;
import org.codehaus.marmalade.parsetime.MarmaladeParsetimeException;
import org.codehaus.marmalade.parsetime.ScriptBuilder;
import org.codehaus.marmalade.parsetime.ScriptParser;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.util.RecordingReader;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class IT1Test extends AbstractIntegrationTestRunner {
    
    public void testRunJellyForEachBasedScript() throws MarmaladeParsetimeException, MarmaladeModelBuilderException, MarmaladeExecutionException {
        String testResource = "it1.jelly";
        
        DefaultContext ctx = new DefaultContext();
        
        ctx.setVariable("items", Arrays.asList(new String[] {"one", "two"}).iterator());
        
        runIntegrationTest(testResource, ctx);
    }
    
}
