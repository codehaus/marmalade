/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;


/**
 * @author jdcasey
 */
public abstract class AbstractConditionalTag extends AbstractMarmaladeTag {
  
  public static final String TEST_ATTRIBUTE = "test";

  /**
   */
  public AbstractConditionalTag(MarmaladeTagInfo tagInfo) {
    super(tagInfo);
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
