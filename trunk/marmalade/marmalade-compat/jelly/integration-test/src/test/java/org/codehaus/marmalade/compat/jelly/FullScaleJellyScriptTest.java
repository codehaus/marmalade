/* Created on Jul 13, 2004 */
package org.codehaus.marmalade.compat.jelly;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

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
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.util.RecordingReader;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class FullScaleJellyScriptTest extends TestCase {
    
    public static final String PLUGIN_RESOURCE = "plugin.jelly";
    
    public void testShouldParseAndExecuteScriptFromClasspathResourceStream() throws MarmaladeParsetimeException, MarmaladeModelBuilderException, MarmaladeExecutionException {
        ClassLoader cloader = getClass().getClassLoader();
        InputStream resourceStream = cloader.getResourceAsStream(PLUGIN_RESOURCE);
        
        MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver(JellyCompatConstants.JELLY_INCLUSIVE_TAGLIB_DEF_STRATEGY);
        ExpressionEvaluator el = new CommonsElExpressionEvaluator();
        
        DefaultParsingContext context = new DefaultParsingContext();
        context.setDefaultExpressionEvaluator(el);
        context.setTaglibResolver(resolver);
        context.setInputLocation(PLUGIN_RESOURCE);
        context.setInput(new RecordingReader(new InputStreamReader(resourceStream)));
        
        ScriptParser parser = new ScriptParser();
        ScriptBuilder builder = parser.parse(context);
        
        MarmaladeScript script = builder.build();
        
        DefaultContext ctx = new DefaultContext();
        
        File baseDir = new File(".");
        ctx.setVariable("dir", new File(baseDir.getAbsoluteFile(), "/nonexistent-target"));
        ctx.setVariable("basedir", baseDir);
        
        script.execute(ctx);
    }

}
