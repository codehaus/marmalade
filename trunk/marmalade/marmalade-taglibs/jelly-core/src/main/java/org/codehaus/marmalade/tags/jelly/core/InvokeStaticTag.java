/* Created on Apr 20, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.commonjava.reflection.Reflector;
import org.commonjava.reflection.ReflectorException;

/**
 * @author jdcasey
 */
public class InvokeStaticTag extends AbstractMarmaladeTag implements ArgParent{
  
  public static final String METHOD_ATTRIBUTE = "method";
  public static final String CLASS_NAME_ATTRIBUTE = "className";
  public static final String VAR_ATTRIBUTE = "var";
  
  private List args = new ArrayList();

  public InvokeStaticTag(MarmaladeTagInfo tagInfo){
      super(tagInfo);
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    String className = (String)requireTagAttribute(CLASS_NAME_ATTRIBUTE, String.class, context);
    String methodName = (String)requireTagAttribute(METHOD_ATTRIBUTE, String.class, context);
    String var = (String)requireTagAttribute(VAR_ATTRIBUTE, String.class, context);
    
    Class targetClass;
    try{
      targetClass = Class.forName(className);
    }
    catch(ClassNotFoundException e){
      throw new MarmaladeExecutionException(
        "Error loading class: " + className, e
      );
    }
    
    Object[] params = null;
    Class[] paramTypes = null;
    if(args.size() > 0) {
      params = (Object[])args.toArray(new Object[args.size()]);
    }
    else {
      params = new Object[0];
    }
    
    Object result;
    try{
      result = Reflector.invokeStatic(targetClass, methodName, params);
    }
    catch(ReflectorException e){
      throw new MarmaladeExecutionException(
        "Error instantiating method: " + methodName + " on class: " + className, e
      );
    }
    
    context.setVariable(var, result);
  }

  public void addArgument(Object arg){
    args.add(arg);
  }
}
