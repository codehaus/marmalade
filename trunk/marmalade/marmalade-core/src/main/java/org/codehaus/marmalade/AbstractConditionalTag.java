/* Created on Apr 10, 2004 */
package org.codehaus.marmalade;


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
    Object value = requireTagAttribute(TEST_ATTRIBUTE, context);
    boolean result = false;
    
    if(value != null){
      if(value instanceof Boolean){
        result = ((Boolean)value).booleanValue();
      }
      else{
        result = Boolean.getBoolean(String.valueOf(value));
      }
    }
    
    return result;
  }
  
  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    processChildren(context);
  }

}
