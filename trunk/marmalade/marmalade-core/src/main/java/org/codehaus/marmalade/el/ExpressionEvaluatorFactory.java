/* Created on Mar 24, 2004 */
package org.codehaus.marmalade.el;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.codehaus.marmalade.ConfigurationException;
import org.commonjava.reflection.FinderException;
import org.commonjava.reflection.ImplFinder;

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
  private ExpressionEvaluatorFactory() {
  }
  
  /** Return the default expression evaluator implementation.
   */
  public static ExpressionEvaluator getDefaultExpressionEvaluator() 
  throws ConfigurationException
  {
    return getExpressionEvaluator(DEFAULT_EL_TYPE);
  }
  
  /** Return the expression evaluator implementation for the specified EL type.
   */
  public static ExpressionEvaluator getExpressionEvaluator(String type) 
  throws ConfigurationException
  {
    // lock only for the specified type of EL...let everything else happen.
    synchronized(type.intern()){
      ExpressionEvaluator evaluator = (ExpressionEvaluator)evaluators.get(type);
      if(evaluator == null){
        String elResource = "META-INF/el/" + type;
        ClassLoader cloader = ExpressionEvaluatorFactory.class.getClassLoader();
        
        InputStream res = cloader.getResourceAsStream(elResource);
        if(res == null){
          throw new ConfigurationException(
            "No ExpressionEvaluator implementation found for EL type: " + type
          );
        }
        else{
          String className = null;
          
          try{
            BufferedReader in = new BufferedReader(new InputStreamReader(res));
            in.readLine();
            in.close();
          }
          catch(IOException e){
            throw new ConfigurationException(
              "Error reading Expression Evaluator implementation class name for type: \'" + 
              type + "\' from \'" + elResource + "\'.",
              e
            );
          }
          
          try {
            Class elClass = cloader.loadClass(className);
            evaluator = (ExpressionEvaluator)elClass.newInstance();
            evaluators.put(type, evaluator);
          }
          catch (ClassNotFoundException e) {
            throw new ConfigurationException(
              "Expression evaluator class: \'" + className + "\' for type: \'" + 
              type + "\' not found.",
              e
            );
          }
          catch (InstantiationException e) {
            throw new ConfigurationException("Error instantiating expression evaluator.", e);
          }
          catch (IllegalAccessException e) {
            throw new ConfigurationException("Error instantiating expression evaluator.", e);
          }
          catch(UndeclaredThrowableException e){
            Throwable t = e.getUndeclaredThrowable();
            throw new ConfigurationException("Error instantiating expression evaluator.", t);
          }
        }
      }
      
      return evaluator;
    }
  }

}
