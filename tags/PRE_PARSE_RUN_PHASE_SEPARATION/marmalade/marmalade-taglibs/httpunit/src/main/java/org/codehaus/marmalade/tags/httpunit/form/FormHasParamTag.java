 /* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.form;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.form.*;
import org.codehaus.marmalade.tags.httpunit.structure.*;


/**
 * @author jdcasey
 */
public class FormHasParamTag extends AbstractFormSubAssertionTag{
  
  public static final String WITH_NAME_ATTRIBUTE = "withName";
  public static final String WITH_VALUE_ATTRIBUTE = "withValue";

  public FormHasParamTag(){
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    String name = (String)requireTagAttribute(WITH_NAME_ATTRIBUTE, String.class, context);
    
    String[] values = getForm(context).getParameterValues(name);
    
    boolean result = false;
    String testVal = (String)getAttributes().getValue(WITH_VALUE_ATTRIBUTE, String.class, context);
    if(testVal == null || testVal.length() < 1) {
      result = values != null && values.length > 0;
    }
    else {
      if(values != null && values.length > 0) {
        for(int i = 0; i < values.length; i++){
          String value = values[i];
          if(value.equals(testVal)) {
            result = true;
            break;
          }
        }
      }
    }
    return result;
  }

}
