/* Created on Jun 23, 2004 */
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.ModelBuilderAttribute;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

/**
 * @author jdcasey
 */
public class DefaultAttribute implements MarmaladeAttribute {
    
    private ExpressionEvaluator el;
    private ModelBuilderAttribute attribute;

    public DefaultAttribute(ModelBuilderAttribute attribute, ExpressionEvaluator el) {
        this.attribute = attribute;
        this.el = el;
    }

    public String getNamespace() {
        return attribute.getNamespace();
    }

    public String getName() {
        return attribute.getName();
    }

    public Object getValue(Class returnType, MarmaladeExecutionContext context) throws ExpressionEvaluationException {
        return _getValue(returnType, context);
    }
    
    public Object getValue(MarmaladeExecutionContext context) throws ExpressionEvaluationException {
        return _getValue(Object.class, context);
    }
    
    private Object _getValue(Class returnType, MarmaladeExecutionContext context) throws ExpressionEvaluationException {
        String expression = attribute.getValue();
        Object result = expression;
        if(expression != null && expression.length() > 0) {
            result = el.evaluate(expression, context.unmodifiableVariableMap(), returnType);
        }
        
        return result;
    }

}
