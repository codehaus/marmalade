/* Created on May 10, 2004 */
package org.codehaus.marmalade.abstractions;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;


/**
 * @author jdcasey
 */
public abstract class AbstractExpressionEvaluator implements ExpressionEvaluator{
  
  private static final Pattern EXPRESSION_PATTERN = Pattern.compile("\\$\\{.*\\}");

  protected AbstractExpressionEvaluator(){
  }

  public Object evaluate(String expression, Map context, Class expectedReturnType)
  throws ExpressionEvaluationException
  {
    Object result = null;
    Matcher matcher = EXPRESSION_PATTERN.matcher(expression);
    if(matcher.matches()) {
      String expr = expression;
      if(trimExpressionDelimiters()) {
        expr = expr.substring(2, expr.length()-1);
      }
      result = doEval(expr, context, expectedReturnType);
    }
    else if(String.class == expectedReturnType){
      matcher.reset();
      StringBuffer resultBuffer = new StringBuffer();
      while(matcher.find()) {
        String expr = matcher.group();
        if(trimExpressionDelimiters()) {
          expr = expr.substring(2, expr.length()-1);
        }
        Object exprResult = doEval(expr, context, String.class);
        matcher.appendReplacement(resultBuffer, String.valueOf(exprResult));
      }
      matcher.appendTail(resultBuffer);
      
      result = resultBuffer.toString();
    }
    
    if(result != null && !expectedReturnType.isAssignableFrom(result.getClass()))
    {
      throw new ExpressionEvaluationException(
        "Result: " + 
        result + 
        " of expression: " + 
        expression + 
        " is not of type: " + 
        expectedReturnType
      );
    }
    
    return result;
  }
  
  protected abstract boolean trimExpressionDelimiters();
  
  protected abstract Object doEval(String expression, Map context, Class expectedType)
  throws ExpressionEvaluationException;

}
