/* Created on Mar 24, 2004 */
package org.codehaus.marmalade;

import org.codehaus.marmalade.el.ExpressionEvaluator;

/** Represents a marmalade tag. Simply used as a common place to specify behavior in the future.
 * 
 * @author John Casey
 */
public interface MarmaladeTag {
  
  public String getBodyText();
  
  public MarmaladeAttributes getAttributes();
  
  public ExpressionEvaluator getExpressionEvaluator() throws ConfigurationException;
  
  public void execute(MarmaladeExecutionContext context) throws MarmaladeExecutionException;
  
  public void processChildren(MarmaladeExecutionContext context) throws MarmaladeExecutionException;
  
}
