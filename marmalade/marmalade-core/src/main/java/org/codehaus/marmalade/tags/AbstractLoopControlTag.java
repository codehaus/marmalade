/* Created on Apr 18, 2004 */
package org.codehaus.marmalade.tags;

import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;


/**
 * @author jdcasey
 */
public abstract class AbstractLoopControlTag extends AbstractConditionalTag{

  protected AbstractLoopControlTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
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
