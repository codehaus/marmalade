 /* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.link;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class LinkHasParamTag extends AbstractLinkSubAssertionTag{
  
  public static final String WITH_NAME_ATTRIBUTE = "withName";
  public static final String WITH_VALUE_ATTRIBUTE = "withValue";

  public LinkHasParamTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    String name = (String)requireTagAttribute(WITH_NAME_ATTRIBUTE, String.class, context);
    
    String[] values = getLink(context).getParameterValues(name);
    
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
