/* Created on Aug 10, 2004 */
package org.codehaus.marmalade.instrumentation;

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
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.util.RecordingReader;

import java.io.StringReader;

/**
 * @author jdcasey
 */
public class SourceLineErrorInjectionAspectTest
    extends TestCase
{
    public void testShouldReportErrorAtLine2() throws MarmaladeParsetimeException, MarmaladeParsetimeException,
        ModelBuilderException
    {
        String testScript = "<?xml version=\"1.0\"?>\n"
            + "<testFailure xmlns=\"marmalade:org.codehaus.marmalade.instrumentation.TestFailureTaglib\"/>";

        StringReader strReader = new StringReader( testScript );
        RecordingReader reader = new RecordingReader( strReader );

        MarmaladeParsingContext context = new DefaultParsingContext();

        context.setInput( reader );
        context.setInputLocation( "test-script" );
        context.addTaglibDefinitionStrategies( MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN );

        ScriptBuilder builder = new ScriptParser().parse( context );

        MarmaladeScript script = builder.build();
        MarmaladeExecutionContext ctx = new DefaultContext();

        try
        {
            script.execute( ctx );
            fail( "Should have thrown an exception" );
        }
        catch ( MarmaladeExecutionException e )
        {
            e.printStackTrace();

            String message = e.getMessage();

            System.out.println( "Error message is: " + message );
            assertTrue( message.indexOf( "test-script[2]" ) > -1 );
        }
    }
}