/* Created on Mar 24, 2004 */
package org.codehaus.marmalade.el;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;
import java.util.WeakHashMap;

import org.codehaus.marmalade.runtime.ConfigurationException;

/** Provide factory methods to retrieve specific implementations for various dynamic elements of
 * the marmalade system.
 * 
 * @author John Casey
 */
public final class ExpressionEvaluatorFactory {
  
  private static final String DEFAULT_EL_TYPE = "ognl";
  
  private static Map evaluators = new WeakHashMap();

  /** Factory; deny construction.
   */
  public ExpressionEvaluatorFactory() {
  }
  
  /** Return the default expression evaluator implementation.
   */
  public static ExpressionEvaluator getDefaultExpressionEvaluator() 
  {
    return new ExpressionEvaluatorFactory().getExpressionEvaluator(DEFAULT_EL_TYPE);
  }
  
  /** Return the expression evaluator implementation for the specified EL type.
   */
  public ExpressionEvaluator getExpressionEvaluator(String type) 
  {
    // lock only for the specified type of EL...let everything else happen.
    synchronized(type.intern()){
      ExpressionEvaluator evaluator = (ExpressionEvaluator)evaluators.get(type);
      if(evaluator == null){
        String elResource = "META-INF/marmalade/el/" + type;
        ClassLoader cloader = ExpressionEvaluatorFactory.class.getClassLoader();
        
        InputStream res = cloader.getResourceAsStream(elResource);
        if(res == null){
          // DO NOT cache this...it is only for emergency cases, to enable minimal functionality.
          return new PassThroughExpressionEvaluator();
        }
        else{
          String className = null;
          BufferedReader in = null;
          try{
            in = new BufferedReader(new InputStreamReader(res));
            className = in.readLine();
            in.close();
          }
          catch(IOException e){
          }
          finally {
            if(in != null) {try {in.close();}catch(IOException e) {}}
          }
          
          if(className != null && className.length() > 0) {
            try {
              Class elClass = cloader.loadClass(className);
              evaluator = (ExpressionEvaluator)elClass.newInstance();
              evaluators.put(type, evaluator);
            }
            catch (ClassNotFoundException e) {
            }
            catch (InstantiationException e) {
            }
            catch (IllegalAccessException e) {
            }
            catch(UndeclaredThrowableException e){
            }
          }
        }
      }
      
      return evaluator;
    }
  }

}
