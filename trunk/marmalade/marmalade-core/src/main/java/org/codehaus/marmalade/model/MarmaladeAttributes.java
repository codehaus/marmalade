/* Created on Mar 25, 2004 */
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

/**
 * @author jdcasey
 */
public interface MarmaladeAttributes{
  
  public ExpressionEvaluator getExpressionEvaluator();
  
  public Object getValue(String name, Class type, MarmaladeExecutionContext context)
  throws ExpressionEvaluationException;
  
  public Object getValue(String name, Class type, MarmaladeExecutionContext context, 
                          Object defaultVal) 
  throws ExpressionEvaluationException;
  
  /** Assume Object.class is the type. */
  public Object getValue(String name, MarmaladeExecutionContext context)
  throws ExpressionEvaluationException;
  
  /** Assume Object.class is the type. */
  public Object getValue(String name, MarmaladeExecutionContext context, 
                          Object defaultVal) 
  throws ExpressionEvaluationException;
  
}