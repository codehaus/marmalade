/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.defaults;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.util.ScopedMap;

/**
 * @author jdcasey
 */
public class DefaultContext implements MarmaladeExecutionContext {
  
  private static final PrintWriter sysout = new PrintWriter(new OutputStreamWriter(System.out));
  private static final PrintWriter syserr = new PrintWriter(new OutputStreamWriter(System.err));
  private static final Reader sysin = new BufferedReader(new InputStreamReader(System.in));
  
  private Map context;
  private PrintWriter out = sysout;
  private PrintWriter err = syserr;
  private Reader in = sysin;

  public DefaultContext() {
    this.context = new HashMap();
  }
  
  public DefaultContext(Map context){
    this.context = context;
  }

  public void setOutWriter(PrintWriter out){
    this.out = out;
  }
  
  public void setErrWriter(PrintWriter err){
    this.err = err;
  }
  
  public void setInReader(Reader in){
    this.in = in;
  }

  public Object getVariable(Object key, ExpressionEvaluator el)
  throws ExpressionEvaluationException
  {
    Object result = context.get(key);
    if(el != null && result != null && (result instanceof String)){
      if(el.isExpression((String)result)) {
        result = el.evaluate((String)result, context, Object.class);
      }
    }
    
    return result;
  }

  public Object setVariable(Object key, Object value) {
    return context.put(key, value);
  }

  public Object removeVariable(Object key) {
    return context.remove(key);
  }

  public Map unmodifiableVariableMap() {
    return Collections.unmodifiableMap(context);
  }

  public void newScope() {
    this.context = new ScopedMap(context);
  }

  public Map lastScope() {
    Map replaced = null;
    if(context instanceof ScopedMap){
      Map parent = ((ScopedMap)context).getSuperMap();
      Map local = ((ScopedMap)context).getLocalMap();
      
      if(parent != null){
        context = parent;
        replaced = local;
      }
    }
    
    return replaced;
  }

  public PrintWriter getErrWriter() {
    return err;
  }

  public PrintWriter getOutWriter() {
    return out;
  }

  public Reader getInReader() {
    return in;
  }

}
