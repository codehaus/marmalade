/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.util;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jdcasey
 */
public final class LazyMansAccess
{
    private LazyMansAccess()
    {
    }

    public static Map executeFromStringForContentGeneration( String scriptName, String scriptContent,
        Map contextVariables, PrintWriter writer ) throws ModelBuilderException, MarmaladeParsetimeException,
        IOException, MarmaladeExecutionException
    {
        StringReader reader = new StringReader( scriptContent );
        RecordingReader rr = new RecordingReader( reader );

        MarmaladeScript script = buildFrom( scriptName, rr );
        Map result = execute( script, contextVariables, writer );

        return result;
    }

    public static Map executeFromFileForContentGeneration( File scriptFile, Map contextVariables, PrintWriter writer )
        throws ModelBuilderException, MarmaladeParsetimeException, IOException, MarmaladeExecutionException
    {
        RecordingReader rr = new RecordingReader( new BufferedReader( new FileReader( scriptFile ) ) );

        MarmaladeScript script = buildFrom( scriptFile.getCanonicalPath(), rr );
        Map result = execute( script, contextVariables, writer );

        return result;
    }

    public static Map executeFromUrlForContentGeneration( URL url, Map contextVariables, PrintWriter writer )
        throws ModelBuilderException, MarmaladeParsetimeException, IOException, MarmaladeExecutionException
    {
        RecordingReader rr = new RecordingReader( new BufferedReader( new InputStreamReader( url.openStream() ) ) );

        MarmaladeScript script = buildFrom( url.toExternalForm(), rr );
        Map result = execute( script, contextVariables, writer );

        return result;
    }

    public static Map executeFromString( String scriptName, String scriptContent, Map contextVariables )
        throws ModelBuilderException, MarmaladeParsetimeException, IOException, MarmaladeExecutionException
    {
        StringReader reader = new StringReader( scriptContent );
        RecordingReader rr = new RecordingReader( reader );

        MarmaladeScript script = buildFrom( scriptName, rr );
        Map result = execute( script, contextVariables, System.out );

        return result;
    }

    public static Map executeFromFile( File scriptFile, Map contextVariables ) throws ModelBuilderException,
        MarmaladeParsetimeException, IOException, MarmaladeExecutionException
    {
        RecordingReader rr = new RecordingReader( new BufferedReader( new FileReader( scriptFile ) ) );

        MarmaladeScript script = buildFrom( scriptFile.getCanonicalPath(), rr );
        Map result = execute( script, contextVariables, System.out );

        return result;
    }

    public static Map executeFromUrl( URL url, Map contextVariables ) throws ModelBuilderException,
        MarmaladeParsetimeException, IOException, MarmaladeExecutionException
    {
        RecordingReader rr = new RecordingReader( new BufferedReader( new InputStreamReader( url.openStream() ) ) );

        MarmaladeScript script = buildFrom( url.toExternalForm(), rr );
        Map result = execute( script, contextVariables, System.out );

        return result;
    }

    private static MarmaladeScript buildFrom( String inputLocation, RecordingReader rr ) throws ModelBuilderException,
        MarmaladeParsetimeException
    {
        MarmaladeParsingContext pCtx = new DefaultParsingContext();

        pCtx.setInput( rr );
        pCtx.setInputLocation( inputLocation );
        pCtx.addTaglibDefinitionStrategies( MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN );

        ScriptParser parser = new ScriptParser();
        ScriptBuilder builder = parser.parse( pCtx );

        return builder.build();
    }

    private static Map execute( MarmaladeScript script, Map contextVariables, PrintStream out )
        throws MarmaladeExecutionException
    {
        MarmaladeExecutionContext ctx = new DefaultContext( contextVariables );

        ctx.setOutWriter( new PrintWriter( out ) );

        script.execute( ctx );

        return new HashMap( ctx.unmodifiableVariableMap() );
    }

    private static Map execute( MarmaladeScript script, Map contextVariables, PrintWriter out )
        throws MarmaladeExecutionException
    {
        MarmaladeExecutionContext ctx = new DefaultContext( contextVariables );

        ctx.setOutWriter( out );

        script.execute( ctx );

        return new HashMap( ctx.unmodifiableVariableMap() );
    }
}