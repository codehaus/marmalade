/* Created on Jul 1, 2004 */
package org.codehaus.marmalade.parsetime;

import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.el.ExpressionEvaluatorFactory;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.util.RecordingReader;

/**
 * @author jdcasey
 */
public class DefaultParsingContext implements MarmaladeParsingContext {

    private ExpressionEvaluator evaluator;
    private ExpressionEvaluatorFactory factory = new ExpressionEvaluatorFactory();
    private MarmaladeTaglibResolver resolver;
    private RecordingReader input;
    private String inputLocation;

    public DefaultParsingContext() {
    }

    public ExpressionEvaluator getDefaultExpressionEvaluator() {
        return evaluator;
    }

    public ExpressionEvaluatorFactory getExpressionEvaluatorFactory() {
        return factory;
    }

    public void setDefaultExpressionEvaluator(
            ExpressionEvaluator evaluator) {
                this.evaluator = evaluator;
    }

    public MarmaladeTaglibResolver getTaglibResolver() {
        return resolver;
    }

    public void setTaglibResolver(MarmaladeTaglibResolver resolver) {
        this.resolver = resolver;
    }

    public RecordingReader getInput() {
        return input;
    }

    public void setInput(RecordingReader input) {
        this.input = input;
    }

    public String getInputLocation() {
        return inputLocation;
    }

    public void setInputLocation(String inputLocation) {
        this.inputLocation = inputLocation;
    }

}
