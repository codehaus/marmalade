/* Created on Apr 18, 2004 */
package org.codehaus.marmalade.generics;

import org.codehaus.marmalade.LoopingTag;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.generics.AbstractConditionalTag;


/**
 * @author jdcasey
 */
public abstract class AbstractLoopControlTag extends AbstractConditionalTag{

  protected AbstractLoopControlTag(){
  }

  public boolean conditionMatches(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    Object value = getAttributes().getValue(
      AbstractConditionalTag.TEST_ATTRIBUTE, Boolean.class, context
    );
    
    boolean result = true;
    if(value != null){
      if(value instanceof Boolean){
        result = ((Boolean)value).booleanValue();
      }
      else{
        String test = String.valueOf(value);
        result = Boolean.getBoolean(test);
      }
    }
    
    return result;
  }
  
  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    requireParent(LoopingTag.class);
    if(conditionMatches(context)) {
      modifyLoop(context, ((LoopingTag)getParent()));
    }
  }
  
  protected abstract void modifyLoop(MarmaladeExecutionContext context, LoopingTag parent)
  throws MarmaladeExecutionException;
  
}
