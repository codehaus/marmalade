/* Created on Apr 10, 2004 */
package org.codehaus.marmalade;

import java.util.Map;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.tagalog.Attributes;

/**
 * @author jdcasey
 */
public class DefaultAttributes implements MarmaladeAttributes {
  
  private ExpressionEvaluator el;
  private Attributes parsedAttributes;

  public DefaultAttributes(ExpressionEvaluator el, Attributes parsedAttributes) {
    this.el = el;
    this.parsedAttributes = parsedAttributes;
  }

  public ExpressionEvaluator getExpressionEvaluator() {
    return el;
  }

  public Object getValue(String name, MarmaladeExecutionContext context)
  throws ExpressionEvaluationException
  {
    return _getValue(name, Object.class, context.unmodifiableVariableMap(), null);
  }
  
  public Object getValue(String name, MarmaladeExecutionContext context, Object defaultVal) 
  throws ExpressionEvaluationException
  {
    return _getValue(name, Object.class, context.unmodifiableVariableMap(), defaultVal);
  }

  public Object getValue(String name, Class type, MarmaladeExecutionContext context)
  throws ExpressionEvaluationException
  {
    return _getValue(name, type, context.unmodifiableVariableMap(), null);
  }
  
  public Object getValue(String name, Class type, MarmaladeExecutionContext context, 
                          Object defaultVal) 
  throws ExpressionEvaluationException
  {
    return _getValue(name, type, context.unmodifiableVariableMap(), defaultVal);
  }

  private Object _getValue(String name, Class type, Map context, Object defaultVal)
  throws ExpressionEvaluationException
  {
    String expression = parsedAttributes.getValue(name);
    Object result = defaultVal;
    if(expression != null && expression.length() > 0){
      if(el != null){
        result = el.evaluate(expression, context, Object.class);
      }
      else{
        result = expression;
      }
    }
    
    return result;
  }

}
