/* Created on Mar 24, 2004 */
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.runtime.ConfigurationException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/** Represents a marmalade tag. Simply used as a common place to specify behavior in the future.
 * 
 * @author John Casey
 */
public interface MarmaladeTag {
  
  Object getBody(MarmaladeExecutionContext context)
  throws ExpressionEvaluationException;

  Object getBody(MarmaladeExecutionContext context, Class targetType)
  throws ExpressionEvaluationException;
  
  MarmaladeAttributes getAttributes();
  
  ExpressionEvaluator getExpressionEvaluator() throws ConfigurationException;
  
  void execute(MarmaladeExecutionContext context) throws MarmaladeExecutionException;
  
  void processChildren(MarmaladeExecutionContext context) throws MarmaladeExecutionException;
  
  void setParent(MarmaladeTag parent);
  
  void addChild(MarmaladeTag child);
  
  MarmaladeTag getParent();
}
