/*
 *
 * Copyright (c) 2004 John Dennis Casey
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */
/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.discovery.TaglibResolutionStrategy;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.metamodel.ModelBuilderException;
import org.codehaus.marmalade.metamodel.ScriptBuilder;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.parsing.DefaultParsingContext;
import org.codehaus.marmalade.parsing.MarmaladeParsetimeException;
import org.codehaus.marmalade.parsing.MarmaladeParsingContext;
import org.codehaus.marmalade.parsing.ScriptParser;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.TaglibResolutionStrategyOwner;
import org.codehaus.marmalade.util.RecordingReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jdcasey
 */
public class ImportTag extends AbstractMarmaladeTag
    implements TaglibResolutionStrategyOwner
{
    public static final String URL_ATTRIBUTE = "url";
    public static final String VAR_ATTRIBUTE = "var";
    public static final String PARSE_ONLY_ATTRIBUTE = "parse-only";
    public static final String RESOLVER_ATTRIBUTE = "resolver";
    
    private List strategies = new LinkedList();

    public ImportTag(  )
    {
        super(  );
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        Object location = requireTagAttribute( URL_ATTRIBUTE, Object.class,
                context );
        URL resource = null;

        if ( location instanceof URL )
        {
            resource = ( URL ) location;
        }
        else if ( location instanceof String )
        {
            try
            {
                resource = new URL( ( String ) location );
            }
            catch ( MalformedURLException e )
            {
                throw new MarmaladeExecutionException( "Error parsing url into java.net.URL.",
                    e );
            }
        }
        else if ( location instanceof File )
        {
            try
            {
                resource = ( ( File ) location ).toURL(  );
            }
            catch ( MalformedURLException e )
            {
                throw new MarmaladeExecutionException( "Error parsing url into java.net.URL.",
                    e );
            }
        }
        else
        {
            throw new MarmaladeExecutionException( 
                "url attribute must be String, java.net.URL, or java.io.File type." );
        }

        MarmaladeAttributes attributes = getAttributes(  );

        RecordingReader reader = null;

        try
        {
            reader = new RecordingReader( new BufferedReader( 
                        new InputStreamReader( resource.openStream(  ) ) ) );

            MarmaladeParsingContext pContext = new DefaultParsingContext(  );

            pContext.setInput( reader );
            pContext.setInputLocation( resource.toExternalForm(  ) );
            pContext.setDefaultExpressionEvaluator( getExpressionEvaluator(  ) );
            
            if(strategies.isEmpty()) {
                pContext.addTaglibDefinitionStrategies(MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN);
            }
            else {
                pContext.addTaglibDefinitionStrategies(strategies);
            }

            ScriptParser parser = new ScriptParser(  );
            ScriptBuilder builder = parser.parse( pContext );

            MarmaladeScript script = builder.build(  );
            Boolean parseOnly = ( Boolean ) attributes.getValue( PARSE_ONLY_ATTRIBUTE,
                    Boolean.class, context, Boolean.FALSE );
            boolean shouldExec = ( parseOnly == null ) ? ( true )
                                                       : ( !parseOnly
                .booleanValue(  ) );

            if ( shouldExec )
            {
                script.execute( context );
            }

            String var = ( String ) attributes.getValue( VAR_ATTRIBUTE,
                    String.class, context );

            if ( ( var != null ) && ( var.length(  ) > 0 ) )
            {
                context.setVariable( var, script );
            }
        }
        catch ( MarmaladeParsetimeException e )
        {
            throw new MarmaladeExecutionException( 
                "Error parsing script from: " + resource.toExternalForm(  ), e );
        }
        catch ( ModelBuilderException e )
        {
            throw new MarmaladeExecutionException( 
                "Error parsing script from: " + resource.toExternalForm(  ), e );
        }
        catch ( IOException e )
        {
            throw new MarmaladeExecutionException( 
                "Error parsing script from: " + resource.toExternalForm(  ), e );
        }
        finally
        {
            if ( reader != null )
            {
                try
                {
                    reader.close(  );
                }
                catch ( IOException e )
                {
                }
            }
        }
    }
    
    protected void doReset() {
        strategies.clear();
    }

    public void addTaglibResolutionStrategy(TaglibResolutionStrategy strategy) {
        if(!strategies.contains(strategy)) {
            strategies.add(strategy);
        }
    }
}
