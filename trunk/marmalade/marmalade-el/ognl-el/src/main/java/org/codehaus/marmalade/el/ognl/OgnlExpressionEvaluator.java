/* Created on Mar 26, 2004 */
package org.codehaus.marmalade.el.ognl;

import java.util.Map;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;

import ognl.Ognl;
import ognl.OgnlException;

/**
 * @author jdcasey
 */
public class OgnlExpressionEvaluator implements ExpressionEvaluator {

  private static final String EXPRESSION_PATTERNS = 
    "null|true|false|(.*\\?.*:.*)|[0-9]+|0x[a-f]+|(.*[#{}].*)";

  public OgnlExpressionEvaluator() {
  }

  public Object evaluate(String expression, Map context, Class expectedReturnType) 
  throws ExpressionEvaluationException
  {
    try {
      Object result = Ognl.getValue(expression, context, (Object)null);
      if(result != null && !expectedReturnType.isAssignableFrom(result.getClass())) {
        throw new ExpressionEvaluationException(
            "Result of evaluation is not of type " + expectedReturnType.getName()
        );
      }
      else {
        return result;
      }
    }
    catch (OgnlException e) {
      throw new ExpressionEvaluationException(expression, e);
    }
  }

  public Object assign(Object target, String property, Object value)
  throws ExpressionEvaluationException
  {
    try {
      Ognl.setValue(property, target, value);
      return target;
    }
    catch (OgnlException e) {
      throw new ExpressionEvaluationException(property, e);
    }
  }

  public boolean isExpression(String src){
    return src.matches(EXPRESSION_PATTERNS);
  }

}
