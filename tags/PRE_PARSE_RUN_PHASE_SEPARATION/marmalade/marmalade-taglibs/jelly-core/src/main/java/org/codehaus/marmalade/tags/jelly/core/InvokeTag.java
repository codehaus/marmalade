/* Created on Apr 20, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.marmalade.MarmaladeAttributes;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;
import org.commonjava.reflection.Reflector;
import org.commonjava.reflection.ReflectorException;


/**
 * @author jdcasey
 */
public class InvokeTag extends AbstractMarmaladeTag implements ArgParent{
  
  public static final String METHOD_ATTRIBUTE = "method";
  public static final String ON_ATTRIBUTE = "on";
  public static final String VAR_ATTRIBUTE = "var";
  
  private List args = new ArrayList();

  public InvokeTag(){
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    Object target = requireTagAttribute(ON_ATTRIBUTE, context);
    String methodName = (String)requireTagAttribute(METHOD_ATTRIBUTE, String.class, context);
    String var = (String)requireTagAttribute(VAR_ATTRIBUTE, String.class, context);
    
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
      result = Reflector.invoke(target, methodName, params);
    }
    catch(ReflectorException e){
      throw new MarmaladeExecutionException(
        "Error instantiating method: " + methodName + " on object: " + target, e
      );
    }
    
    context.setVariable(var, result);
  }

  public void addArgument(Object arg){
    args.add(arg);
  }
}
