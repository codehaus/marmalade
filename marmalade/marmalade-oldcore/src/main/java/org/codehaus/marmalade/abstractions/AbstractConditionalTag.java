/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.abstractions;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;


/**
 * @author jdcasey
 */
public abstract class AbstractConditionalTag extends AbstractMarmaladeTag {
  
  public static final String TEST_ATTRIBUTE = "test";

  /**
   */
  public AbstractConditionalTag() {
  }
  
  public boolean conditionMatches(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    Object value = requireTagAttribute(TEST_ATTRIBUTE, Boolean.class, context);
    
    boolean result = false;
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
  
  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    processChildren(context);
  }

}
