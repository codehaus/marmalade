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

    public static Map executeFromFile( File scriptFile, Map contextVariables ) throws ModelBuilderException,
        MarmaladeParsetimeException, IOException, MarmaladeExecutionException
    {
        RecordingReader rr = new RecordingReader( new BufferedReader( new FileReader( scriptFile ) ) );

        MarmaladeScript script = buildFrom( scriptFile.getCanonicalPath(), rr );
        Map result = execute( script, contextVariables );

        return result;
    }

    public static Map executeFromUrl( URL url, Map contextVariables ) throws ModelBuilderException,
        MarmaladeParsetimeException, IOException, MarmaladeExecutionException
    {
        RecordingReader rr = new RecordingReader( new BufferedReader( new InputStreamReader( url.openStream() ) ) );

        MarmaladeScript script = buildFrom( url.toExternalForm(), rr );
        Map result = execute( script, contextVariables );

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

    private static Map execute( MarmaladeScript script, Map contextVariables ) throws MarmaladeExecutionException
    {
        MarmaladeExecutionContext ctx = new DefaultContext( contextVariables );

        script.execute( ctx );

        return new HashMap( ctx.unmodifiableVariableMap() );
    }
}