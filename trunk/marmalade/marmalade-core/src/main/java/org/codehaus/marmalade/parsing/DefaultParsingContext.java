/* Created on Jul 1, 2004 */
package org.codehaus.marmalade.parsing;

import java.io.Reader;
import java.util.Collection;

import org.codehaus.marmalade.discovery.TaglibResolutionStrategy;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.el.ExpressionEvaluatorFactory;
import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.monitor.log.MarmaladeLog;
import org.codehaus.marmalade.util.RecordingReader;

/**
 * @author jdcasey
 */
public class DefaultParsingContext
    implements MarmaladeParsingContext
{
    private ExpressionEvaluator evaluator;

    private ExpressionEvaluatorFactory factory = new ExpressionEvaluatorFactory();

    private MarmaladeTaglibResolver resolver;

    private RecordingReader input;

    private String inputLocation;

    private MarmaladeLog log;

    public DefaultParsingContext()
    {
        this.resolver = new MarmaladeTaglibResolver();
    }

    public ExpressionEvaluator getDefaultExpressionEvaluator()
    {
        return evaluator;
    }

    public ExpressionEvaluatorFactory getExpressionEvaluatorFactory()
    {
        return factory;
    }

    public void setDefaultExpressionEvaluator( ExpressionEvaluator evaluator )
    {
        this.evaluator = evaluator;
    }

    public MarmaladeTaglibResolver getTaglibResolver()
    {
        return resolver;
    }

    public void addTaglibDefinitionStrategy( TaglibResolutionStrategy strategy )
    {
        resolver.addTaglibDefinitionStrategy( strategy );
    }

    public void addTaglibDefinitionStrategies( Collection strategies )
    {
        resolver.addTaglibDefinitionStrategies( strategies );
    }

    public void setTaglibDefinitionStrategies( Collection strategies )
    {
        resolver.setTaglibDefinitionStrategies( strategies );
    }

    public RecordingReader getInput()
    {
        return input;
    }

    public void setInput( Reader input )
    {
        if ( input instanceof RecordingReader )
        {
            this.input = (RecordingReader) input;
        }
        else
        {
            this.input = new RecordingReader( input );
        }
    }

    public String getInputLocation()
    {
        return inputLocation;
    }

    public void setInputLocation( String inputLocation )
    {
        this.inputLocation = inputLocation;
    }

    public void setDefaultTagLibrary( MarmaladeTagLibrary taglib )
    {
        resolver.setDefaultTagLibrary( taglib );
    }

    public void setLog( MarmaladeLog log )
    {
        this.log = log;

        resolver.setLog( log );
        factory.setLog( log );

        if ( evaluator != null )
        {
            evaluator.setLog( log );
        }
    }

    public MarmaladeLog getLog()
    {
        return log;
    }

    public void setClassLoader( ClassLoader classloader )
    {
        resolver.setClassLoader( classloader );
    }
}