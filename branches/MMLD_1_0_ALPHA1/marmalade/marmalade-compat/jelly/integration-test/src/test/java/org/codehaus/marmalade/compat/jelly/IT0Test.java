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
public class IT0Test extends AbstractIntegrationTestRunner {
    
    public void testRunModifiedMaven1CleanPluginScript() throws MarmaladeParsetimeException, MarmaladeModelBuilderException, MarmaladeExecutionException {
        String testResource = "it0.jelly";
        
        DefaultContext ctx = new DefaultContext();
        
        File baseDir = new File(".");
        ctx.setVariable("dir", new File(baseDir.getAbsoluteFile(), "/nonexistent-target"));
        ctx.setVariable("basedir", baseDir);
        
        runIntegrationTest(testResource, ctx);
    }
    
}
