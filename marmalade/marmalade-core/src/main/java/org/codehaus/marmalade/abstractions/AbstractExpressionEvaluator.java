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
  private static final String LITERAL_PATTERNS = "[0-9]+[idfblhIDFBLH]?|true|false|0x[0-9]+[bB]?";

  protected AbstractExpressionEvaluator(){
  }

  public Object evaluate(String expression, Map context, Class expectedReturnType)
  throws ExpressionEvaluationException
  {
    Object result = null;
    Matcher matcher = getExpressionPattern().matcher(expression);
    if(matcher.matches() || expression.matches(LITERAL_PATTERNS))
    {
      result = doEval(expression, context, expectedReturnType);
    }
    else {
      matcher.reset();
      StringBuffer resultBuffer = new StringBuffer();
      while(matcher.find()) {
        String expr = matcher.group();
        Object exprResult = doEval(expr, context, String.class);
        matcher.appendReplacement(resultBuffer, String.valueOf(exprResult));
      }
      matcher.appendTail(resultBuffer);
      
      result = resultBuffer.toString();
    }
    
    if(result != null && !expectedReturnType.isAssignableFrom(result.getClass()))
    {
      throw new ExpressionEvaluationException(
        "Result: \'" + 
        result + 
        "\' of expression: " + 
        expression + 
        " is of type: " +
        result.getClass().getName() + 
        " not of type: " +
        expectedReturnType
      );
    }
    
    return result;
  }
  
  protected abstract Pattern getExpressionPattern();
  
  protected abstract Object doEval(String expression, Map context, Class expectedType)
  throws ExpressionEvaluationException;

}
