/* Created on Mar 24, 2004 */
package org.codehaus.marmalade.el;

import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/** Exception signalling that an error has occurred while evaluating an expression.
 * 
 * @author John Casey
 */
public class ExpressionEvaluationException extends MarmaladeExecutionException {
  
  /** Construct a new exception to convey failure in evaluating an expression.
   * @param message The message giving detailed info about this exception
   */
  public ExpressionEvaluationException(String message) {
    super(message);
  }

  /** Construct a new exception to convey failure in evaluating an expression.
   * @param expression The expression that could not be evaluated.
   * @param cause The root cause of this exception
   */
  public ExpressionEvaluationException(String expression, Throwable cause) {
    super("Error evaluating expression: " + expression, cause);
  }

}
