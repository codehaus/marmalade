/* Created on Mar 24, 2004 */
package org.codehaus.marmalade.el;

import java.util.Map;

/** Represents a bridge to an expression language processor which is used to resolve
 * object expressions within the parsing process.
 * 
 * @author John Casey
 */
public interface ExpressionEvaluator {
  
  public Object evaluate(String expression, Map context, Class expectedReturnType)
  throws ExpressionEvaluationException;
  
  public Object assign(Object target, String property, Object value)
  throws ExpressionEvaluationException;

}
