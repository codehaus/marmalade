package org.codehaus.marmalade.launch;

import org.codehaus.marmalade.discovery.TaglibResolutionStrategy;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;
import org.codehaus.marmalade.metamodel.ModelBuilderException;
import org.codehaus.marmalade.metamodel.ScriptBuilder;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.monitor.log.MarmaladeLog;
import org.codehaus.marmalade.parsing.DefaultParsingContext;
import org.codehaus.marmalade.parsing.MarmaladeParsetimeException;
import org.codehaus.marmalade.parsing.ScriptParser;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

/**
 * @author jdcasey
 */
public class MarmaladeLauncher
{

    private final DefaultParsingContext parsingContext = new DefaultParsingContext();

    private final DefaultContext executionContext = new DefaultContext();

    private ScriptParser scriptParser;

    private boolean returnUnexternalized = false;
    
    public MarmaladeLauncher withUnexternalizedVarsReturned(boolean returnUnexternalized)
    {
        this.returnUnexternalized = returnUnexternalized;
        
        return this;
    }

    public MarmaladeLauncher withAdditionalTaglibDefinitionStrategies( Collection strategies )
    {
        parsingContext.addTaglibDefinitionStrategies( strategies );
        return this;
    }

    public MarmaladeLauncher withAdditionalTaglibDefinitionStrategy( TaglibResolutionStrategy strategy )
    {
        parsingContext.addTaglibDefinitionStrategy( strategy );
        return this;
    }

    public MarmaladeLauncher withDefaultExpressionEvaluator( ExpressionEvaluator evaluator )
    {
        parsingContext.setDefaultExpressionEvaluator( evaluator );
        return this;
    }

    public MarmaladeLauncher withDefaultTagLibrary( MarmaladeTagLibrary taglib )
    {
        parsingContext.setDefaultTagLibrary( taglib );
        return this;
    }

    public MarmaladeLauncher withInput( Reader input )
    {
        parsingContext.setInput( input );
        return this;
    }

    public MarmaladeLauncher withInputURL( URL url ) throws IOException
    {
        parsingContext.setInputLocation( url.toExternalForm() );
        parsingContext.setInput( new InputStreamReader( url.openStream() ) );

        return this;
    }

    public MarmaladeLauncher withInputFile( File file ) throws IOException
    {
        parsingContext.setInputLocation( file.getCanonicalPath() );
        parsingContext.setInput( new BufferedReader( new FileReader( file ) ) );

        return this;
    }

    public MarmaladeLauncher withInputScriptString( String script, String locationDescription )
    {
        parsingContext.setInputLocation( locationDescription );
        parsingContext.setInput( new StringReader( script ) );

        return this;
    }

    public MarmaladeLauncher withInputLocation( String inputLocation )
    {
        parsingContext.setInputLocation( inputLocation );
        return this;
    }

    public MarmaladeLauncher withTaglibDefinitionStrategies( Collection strategies )
    {
        parsingContext.setTaglibDefinitionStrategies( strategies );
        return this;
    }

    public MarmaladeLauncher withImportedContext( MarmaladeExecutionContext context )
    {
        executionContext.importContext( context );
        return this;
    }

    public MarmaladeLauncher withPreserveWhitespaceOverride( Boolean shouldOverride )
    {
        executionContext.preserveWhitespaceOverride( shouldOverride );
        return this;
    }

    public MarmaladeLauncher withPreserveWhitespaceOverride( boolean shouldOverride )
    {
        executionContext.preserveWhitespaceOverride( Boolean.valueOf( shouldOverride ) );
        return this;
    }

    public MarmaladeLauncher withoutVariable( Object key )
    {
        executionContext.removeVariable( key );
        return this;
    }

    public MarmaladeLauncher withErrWriter( PrintWriter err )
    {
        executionContext.setErrWriter( err );
        return this;
    }

    public MarmaladeLauncher withInReader( Reader in )
    {
        executionContext.setInReader( in );
        return this;
    }

    public MarmaladeLauncher withLog( MarmaladeLog log )
    {
        executionContext.setLog( log );
        return this;
    }

    public MarmaladeLauncher withOutWriter( PrintWriter out )
    {
        executionContext.setOutWriter( out );
        return this;
    }

    public MarmaladeLauncher withVariable( Object key, Object value )
    {
        executionContext.setVariable( key, value );
        return this;
    }

    public MarmaladeLauncher withVariable( Object key, Object value, boolean externalize )
    {
        executionContext.setVariable( key, value, externalize );
        return this;
    }

    public MarmaladeLauncher withVariables( Map vars )
    {
        executionContext.setVariables( vars );
        return this;
    }

    public MarmaladeLauncher withVariables( Map vars, boolean externalize )
    {
        executionContext.setVariables( vars, externalize );
        return this;
    }

    public MarmaladeLauncher withParser( ScriptParser parser )
    {
        this.scriptParser = parser;
        return this;
    }

    public Map run() throws MarmaladeLaunchException
    {
        ScriptBuilder builder = null;
        try
        {
            if ( scriptParser == null )
            {
                scriptParser = new ScriptParser();
            }

            builder = scriptParser.parse( parsingContext );
        }
        catch ( MarmaladeParsetimeException e )
        {
            throw new MarmaladeLaunchException( "Cannot launch marmalade script. There was a parsetime error.", e );
        }

        MarmaladeScript script = null;
        try
        {
            script = builder.build();
        }
        catch ( ModelBuilderException e )
        {
            throw new MarmaladeLaunchException( "Cannot launch marmalade script. There was a problem building script hierarchy from metadata.",
                                                e );
        }

        try
        {
            script.execute( executionContext );
        }
        catch ( MarmaladeExecutionException e )
        {
            throw new MarmaladeLaunchException( "Cannot launch marmalade script. There was an execution-time (runtime) error.",
                                                e );
        }

        Map vars = null;
        if(!returnUnexternalized)
            {
            vars = executionContext.getExternalizedVariables();
            }
        else
        {
            vars = executionContext.getVariablesAsResolved();
        }
        
        return vars;
    }

}