/* Created on Apr 10, 2004 */
package org.codehaus.marmalade;

import java.io.PrintWriter;
import java.io.Reader;
import java.util.Map;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;

/**
 * @author jdcasey
 */
public interface MarmaladeExecutionContext {
  
  public Object getVariable(Object key, ExpressionEvaluator el)
  throws ExpressionEvaluationException;
  
  public Object setVariable(Object key, Object value);
  
  public Object removeVariable(Object key);
  
  public Map unmodifiableVariableMap();
  
  public void newScope();
  
  public Map lastScope();
  
  public void setOutWriter(PrintWriter out);
  
  public void setErrWriter(PrintWriter err);
  
  public void setInReader(Reader in);
  
  public PrintWriter getErrWriter();
  
  public PrintWriter getOutWriter();
  
  public Reader getInReader();

}
