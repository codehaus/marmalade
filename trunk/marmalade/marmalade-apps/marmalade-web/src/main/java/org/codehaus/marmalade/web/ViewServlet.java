/* Created on Aug 22, 2004 */
package org.codehaus.marmalade.web;

import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.metamodel.ModelBuilderException;
import org.codehaus.marmalade.metamodel.ScriptBuilder;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.parsing.CachingScriptParser;
import org.codehaus.marmalade.parsing.DefaultParsingContext;
import org.codehaus.marmalade.parsing.MarmaladeParsetimeException;
import org.codehaus.marmalade.parsing.MarmaladeParsingContext;
import org.codehaus.marmalade.parsing.ScriptParser;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.util.RecordingReader;
import org.codehaus.marmalade.web.config.ViewServletConfig;
import org.codehaus.marmalade.web.config.tags.ViewServletConfigTag;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author jdcasey
 */
public class ViewServlet
    extends HttpServlet
{
    public static final String VIEW_CONFIG = "viewConfig";

    public static final String MMLD_RELOAD_PARAMETER = "mmld::Reload";

    public static final String MMLD_CONTEXT_ATTRIBUTE = "mmld::Context";

    private ViewServletConfig viewConfig;

    private CachingScriptParser parser = new CachingScriptParser();

    private ServletContext servletContext;

    public void init( ServletConfig servletConfig ) throws ServletException
    {
        this.servletContext = servletConfig.getServletContext();

        String configPath = servletConfig.getInitParameter( VIEW_CONFIG );

        if ( configPath == null )
        {
            throw new ServletException( VIEW_CONFIG + " init parameter is required" );
        }

        String configScriptPath = servletConfig.getServletContext().getRealPath( configPath );

        this.viewConfig = buildConfig( configScriptPath );
    }

    protected void service( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
        IOException
    {
        ViewSource source = viewConfig.getViewFinder().find( request );
        MarmaladeParsingContext parsingContext = source.openContext();

        parsingContext.addTaglibDefinitionStrategies( viewConfig.getTagStrategies() );

        MarmaladeScript script = getScript( parsingContext, request );

        MarmaladeExecutionContext ctx = getContext( request, response );

        try
        {
            request.setAttribute( MMLD_CONTEXT_ATTRIBUTE, ctx );

            StringWriter writer = new StringWriter();
            PrintWriter out = new PrintWriter( writer );

            ctx.setOutWriter( out );

            script.execute( ctx );

            out.flush();

            response.getWriter().print( writer.toString() );
        }
        catch ( MarmaladeExecutionException e )
        {
            throw new ServletException( "cannot execute view script", e );
        }
    }

    private MarmaladeExecutionContext getContext( HttpServletRequest request, HttpServletResponse response )
    {
        MarmaladeExecutionContext ctx = (MarmaladeExecutionContext) request.getAttribute( MMLD_CONTEXT_ATTRIBUTE );

        if ( ctx != null )
        {
            ctx.newScope();
        }
        else
        {
            ctx = new DefaultContext();
            ctx.setVariable( "request", request );
            ctx.setVariable( "response", response );
            ctx.setVariable( "servletContext", servletContext );
        }

        return ctx;
    }

    private MarmaladeScript getScript( MarmaladeParsingContext parsingContext, HttpServletRequest request )
        throws ServletException
    {
        ScriptBuilder builder = null;

        try
        {
            builder = parser.parse( parsingContext );
        }
        catch ( MarmaladeParsetimeException e )
        {
            throw new ServletException( "cannot parse view script", e );
        }

        String reloadParamRaw = request.getParameter( MMLD_RELOAD_PARAMETER );

        if ( (reloadParamRaw != null) && (Boolean.TRUE == Boolean.valueOf( reloadParamRaw )) )
        {
            parser.purge( parsingContext.getInputLocation() );
        }

        MarmaladeScript script = null;

        try
        {
            script = builder.build();
        }
        catch ( ModelBuilderException e )
        {
            throw new ServletException( "cannot build view script", e );
        }

        return script;
    }

    private ViewServletConfig buildConfig( String configScriptPath ) throws ServletException
    {
        MarmaladeScript configScript = null;

        try
        {
            configScript = buildScript( configScriptPath );
        }
        catch ( FileNotFoundException e )
        {
            throw new ServletException( "cannot find view config file", e );
        }
        catch ( MarmaladeParsetimeException e )
        {
            throw new ServletException( "cannot parse view config file", e );
        }
        catch ( ModelBuilderException e )
        {
            throw new ServletException( "cannot build view config script", e );
        }

        ViewServletConfigTag root = (ViewServletConfigTag) configScript.getRoot();
        MarmaladeExecutionContext ctx = new DefaultContext();

        try
        {
            configScript.execute( ctx );
        }
        catch ( MarmaladeExecutionException e )
        {
            throw new ServletException( "cannot configure view servlet", e );
        }

        return root.getConfig();
    }

    private MarmaladeScript buildScript( String configScriptPath ) throws FileNotFoundException,
        MarmaladeParsetimeException, ModelBuilderException
    {
        RecordingReader rr = new RecordingReader( new BufferedReader( new FileReader( configScriptPath ) ) );

        MarmaladeParsingContext parsingContext = new DefaultParsingContext();

        parsingContext.setInput( rr );
        parsingContext.setInputLocation( configScriptPath );
        parsingContext.addTaglibDefinitionStrategies( MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN );

        ScriptBuilder builder = new ScriptParser().parse( parsingContext );
        MarmaladeScript script = builder.build();

        return script;
    }
}