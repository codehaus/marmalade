/* Created on Jul 1, 2004 */
package org.codehaus.marmalade.parsetime;

import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.el.ExpressionEvaluatorFactory;
import org.codehaus.marmalade.el.PassThroughExpressionEvaluator;

/**
 * @author jdcasey
 */
public class DefaultParsingContext implements MarmaladeParsingContext {

    private ExpressionEvaluator evaluator;
    private ExpressionEvaluatorFactory factory = new ExpressionEvaluatorFactory();

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

}
