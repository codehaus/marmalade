/* Created on Mar 26, 2004 */
package org.codehaus.marmalade.el.ognl;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import ognl.DefaultTypeConverter;
import ognl.Ognl;
import ognl.OgnlException;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.abstractions.AbstractExpressionEvaluator;

/**
 * @author jdcasey
 */
public class OgnlExpressionEvaluator extends AbstractExpressionEvaluator {
  
  public static final Pattern EXPRESSION_PATTERN = Pattern.compile("#[^ ]+");

  public OgnlExpressionEvaluator() {
  }

  public Object doEval(String expression, Map context, Class expectedType) 
  throws ExpressionEvaluationException
  {
    try {
      Object result = Ognl.getValue(expression, context, (Object)null);
      return result;
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

  protected Pattern getExpressionPattern(){
    return EXPRESSION_PATTERN;
  }
  
}
