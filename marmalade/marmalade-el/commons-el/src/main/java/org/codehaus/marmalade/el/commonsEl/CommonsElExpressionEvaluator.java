/* Created on Mar 26, 2004 */
package org.codehaus.marmalade.el.commonsEl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

import org.apache.commons.el.Coercions;
import org.apache.commons.el.ExpressionEvaluatorImpl;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;

/**
 * @author jdcasey
 */
public class CommonsElExpressionEvaluator implements ExpressionEvaluator {

  private static final String EXPRESSION_PATTERNS = 
    "\\$\\{.*\\}";
  
  private ExpressionEvaluatorImpl elImpl = new ExpressionEvaluatorImpl(true);

  public CommonsElExpressionEvaluator() {
  }

  public Object evaluate(String expression, Map context, Class expectedReturnType) 
  throws ExpressionEvaluationException
  {
    Object result = null;
    if(expression != null) {
      if(expression.matches(EXPRESSION_PATTERNS)) {
        result = eval(expression, context, expectedReturnType);
      }
      else if(!expectedReturnType.equals(Object.class)){
        try{
          result = Coercions.coerce(expression, expectedReturnType);
        }
        catch(ELException e){
          throw new ExpressionEvaluationException(
            "Error coercing expression: \'" + 
              expression + 
              "\' to class: " + 
              expectedReturnType.getName(), 
            e
          );
        }
      }
      else {
        result = expression;
      }
    }
    
    return result;
  }

  public Object assign(Object targetObject, String propertyName, Object value)
  throws ExpressionEvaluationException
  {
    Object result = null;
    if(targetObject == null) {
      throw new ExpressionEvaluationException("Cannot set property on null target.");
    }
    else if(propertyName == null || propertyName.length() < 1) {
      throw new ExpressionEvaluationException("Cannot set empty property.");
    }
    else {
      Object target = targetObject;
      String property = propertyName;
      
      int lastDot = property.lastIndexOf('.');
      if(lastDot > -1) {
        String expr = "${target." + property.substring(0, lastDot) + "}";
        Map ctx = new TreeMap();
        target = eval(expr, ctx, Object.class);
      }
      
      result = set(target, property, value);
    }
    
    return result;
  }

  /** Returns true, so everything is subject to coercion.
   */
  public boolean isExpression(String src){
    return true;
  }
  
  private Object set(Object target, String property, Object value) throws ExpressionEvaluationException{
    Object result = null;
    try {
      Method method = findMethod(target, property, value);
      
      if(method != null) {
        result = method.invoke(target, new Object[] {value});
      }
      else {
        Field field = target.getClass().getField(property);
        if(field.isAccessible() && field.getType().isAssignableFrom(value.getClass())) {
          field.set(target, value);
          result = value;
        }
        else {
          throw new ExpressionEvaluationException(
            "Cannot set property: \'" + property + "\' on target: " + target
          );
        }
      }
    }
    catch(IllegalArgumentException e){
      throw new ExpressionEvaluationException("Error assigning value to property field.", e);
    }
    catch(IllegalAccessException e){
      throw new ExpressionEvaluationException("Error assigning value to property field.", e);
    }
    catch(SecurityException e){
      throw new ExpressionEvaluationException("Error assigning value to property field.", e);
    }
    catch(NoSuchFieldException e){
      throw new ExpressionEvaluationException(
        "Property not found: \'"+ property + "\' on target: " + target, e
      );
    }
    catch(InvocationTargetException e){
      throw new ExpressionEvaluationException("Error assigning value via property method.", e);
    }
    
    return result;
  }
  
  private Method findMethod(Object target, String property, Object value) {
    StringBuffer propNameBuffer = new StringBuffer(property.length() + 3);
    propNameBuffer.append("set")
            .append(Character.toUpperCase(property.charAt(0)))
            .append(property.substring(1));
    
    String methodName = propNameBuffer.toString();
    Class targetClass = target.getClass();
    
    Method method = null;
    if(value != null) {
      Class[] paramClasses = {value.getClass()};
      try{
        method = targetClass.getMethod(methodName, paramClasses);
      }
      catch(NoSuchMethodException e){
        // Ignore and return null, so we can move on to try field property access.
      }
    }
    else {
      Method[] methods = targetClass.getMethods();
      for(int i = 0; i < methods.length; i++){
        Method candidate = methods[i];
        Class[] paramTypes = candidate.getParameterTypes();
        if(methodName.equals(candidate.getName()) && paramTypes != null && paramTypes.length == 1)
        {
          method = candidate;
        }
      }
    }
    
    return method;
  }
  
  private Object eval(String expression, Map context, Class expectedReturnType) 
  throws ExpressionEvaluationException
  {
    try {
      Object result = elImpl.evaluate(
        expression, expectedReturnType, new ElVarResolver(context), new ElFuncMapper()
      );

      if(result != null && !expectedReturnType.isAssignableFrom(result.getClass())) {
        throw new ExpressionEvaluationException(
            "Result of evaluation is not of type " + expectedReturnType.getName()
        );
      }
      else {
        return result;
      }
    }
    catch(ELException e){
      throw new ExpressionEvaluationException(expression, e);
    }
  }
  
/*  
  public boolean isExpression(String src){
    return src.matches(EXPRESSION_PATTERNS);
  }
*/  
  private static final class ElVarResolver implements VariableResolver{
    private Map context;
    
    ElVarResolver(Map context){
      this.context = context;
    }

    public Object resolveVariable(String name) throws ELException{
      return context.get(name);
    }
  }
  
  private static final class ElFuncMapper implements FunctionMapper{
    
    ElFuncMapper(){
    }

    public Method resolveFunction(String arg0, String arg1){
      return null;
    }
  }

}
