/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.el;

import java.util.Map;


/**
 * @author jdcasey
 */
public class PassThroughExpressionEvaluator implements ExpressionEvaluator{

  public PassThroughExpressionEvaluator(){
  }

  public boolean isExpression(String src){
    return false;
  }

  public Object evaluate(String expression, Map context, Class expectedReturnType)
  throws ExpressionEvaluationException
  {
    return expression;
  }

  public Object assign(Object target, String property, Object value)
  throws ExpressionEvaluationException
  {
    throw new ExpressionEvaluationException(
      "PassThrough evaluator not equipped for this. " +
      "Please ensure that the ognl evaluator is on the classpath."
    );
  }

}
