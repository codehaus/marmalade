/* Created on Mar 26, 2004 */
package org.codehaus.marmalade.el.ognl;

import java.util.Map;
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

  public OgnlExpressionEvaluator() {
  }

  public Object doEval(String expression, Map context, Class expectedType) 
  throws ExpressionEvaluationException
  {
    try {
      Ognl.setTypeConverter(context, new DefaultTypeConverter());
      Object result = Ognl.getValue(expression, context, (Object)null, expectedType);
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

  protected boolean trimExpressionDelimiters(){
    return true;
  }
  
}
