// TODO Attach license header here.
package org.codehaus.marmalade.msp;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.marmalade.discovery.TaglibResolutionStrategy;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.metamodel.ScriptBuilder;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.msp.cache.CacheManager;
import org.codehaus.marmalade.msp.ctx.ContextProvider;
import org.codehaus.marmalade.msp.fault.ScriptFaultHandler;
import org.codehaus.marmalade.msp.finder.ScriptFinder;
import org.codehaus.marmalade.parsing.DefaultParsingContext;
import org.codehaus.marmalade.parsing.MarmaladeParsingContext;
import org.codehaus.marmalade.parsing.ScriptParser;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.tags.passthrough.PassThroughTagLibrary;

/**
 * @author jdcasey
 *
 * Created on Nov 26, 2004
 */
public class MSPHandler
{
    
    public static final String BEAN_ID = "MSPHandler";
    
    private ScriptFinder scriptFinder;
    private ContextProvider contextProvider;
    private Set taglibDefinitionStrategies;
    private ExpressionEvaluator defaultExpressionEvaluator;
    private CacheManager cacheManager;
    private MarmaladeTagLibrary defaultTagLibrary = new PassThroughTagLibrary();
    
    private transient ScriptParser scriptParser = new ScriptParser();
    private ScriptFaultHandler scriptFaultHandler;

    public void setScriptFinder(ScriptFinder scriptFinder)
    {
        this.scriptFinder = scriptFinder;
    }
    
    public void setContextProvider(ContextProvider contextProvider)
    {
        this.contextProvider = contextProvider;
    }
    
    public void setCacheManager(CacheManager cacheManager)
    {
        this.cacheManager = cacheManager;
    }
    
    public void setTaglibDefinitionStrategies(Set taglibDefinitionStrategies)
    {
        this.taglibDefinitionStrategies = taglibDefinitionStrategies;
    }
    
    public void setTaglibDefinitionStrategy(TaglibResolutionStrategy taglibDefinitionStrategy)
    {
        this.taglibDefinitionStrategies = new HashSet();
        this.taglibDefinitionStrategies.add(taglibDefinitionStrategy);
    }
    
    public void setScriptFaultHandler(ScriptFaultHandler scriptFaultHandler)
    {
        this.scriptFaultHandler = scriptFaultHandler;
    }
    
    public void setDefaultExpressionEvaluator(ExpressionEvaluator defaultExpressionEvaluator)
    {
        this.defaultExpressionEvaluator = defaultExpressionEvaluator;
    }
    
    public void setDefaultTagLibrary(MarmaladeTagLibrary defaultTagLibrary)
    {
        this.defaultTagLibrary = defaultTagLibrary;
    }
    
    public synchronized void execute(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        validateState();
        
        try
        {
            MarmaladeScript script = null;

            if ( cacheManager != null )
            {
                script = cacheManager.lookup( request );
            }

            if ( script == null )
            {
                MarmaladeParsingContext parsingContext = new DefaultParsingContext();

                if ( taglibDefinitionStrategies != null && !taglibDefinitionStrategies.isEmpty() )
                {
                    parsingContext.setTaglibDefinitionStrategies( taglibDefinitionStrategies );
                }
                else
                {
                    parsingContext.setTaglibDefinitionStrategies(MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN);
                }

                if ( defaultExpressionEvaluator != null )
                {
                    parsingContext.setDefaultExpressionEvaluator( defaultExpressionEvaluator );
                }

                if ( defaultTagLibrary != null )
                {
                    parsingContext.setDefaultTagLibrary( defaultTagLibrary );
                }

                System.out.println("Getting script reader.");
                Reader scriptReader = scriptFinder.getScript( request);
                
                parsingContext.setInput( scriptReader );
                parsingContext.setInputLocation( request.getRequestURI() );

                System.out.println("Building script");
                ScriptBuilder scriptBuilder = scriptParser.parse( parsingContext );
                script = scriptBuilder.build();
                
                if(cacheManager != null)
                {
                    System.out.println("Caching script");
                    cacheManager.cache(request, script);
                }
            }
            
            System.out.println("Building execution context");
            MarmaladeExecutionContext executionContext = contextProvider.buildContext(request, response);
            
            System.out.println("Setting output to response writer");
            executionContext.setOutWriter(new PrintWriter(response.getWriter()));
            
            System.out.println("Executing script");
            script.execute(executionContext);
            
            System.out.println("Exporting script context result");
            contextProvider.exportContext(executionContext, request, response);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            
            if(e instanceof RuntimeException)
            {
                throw (RuntimeException)e;
            }
            else
            {
                scriptFaultHandler.handleFault(request, response, e);
            }
        }
        
    }

    private void validateState()
    {
        List stateViolations = new ArrayList();
        
        if(scriptFaultHandler == null)
        {
            stateViolations.add(new IllegalStateException("scriptFaultHandler property must be specified for MSPHandler instance."));
        }
        
        if(scriptFinder == null)
        {
            stateViolations.add(new IllegalStateException("scriptFinder property must be specified for MSPHandler instance."));
        }
        
        if(contextProvider == null)
        {
            stateViolations.add(new IllegalStateException("contextProvider property must be specified for MSPHandler instance."));
        }
        
        if(!stateViolations.isEmpty())
        {
            throw new InvalidHandlerStateException(stateViolations);
        }
    }

}
