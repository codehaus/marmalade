/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.structure.*;

import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class HasContentType extends AbstractAssertionTag{
  
  public static final String WITH_VALUE_ATTRIBUTE = "withValue";

  public HasContentType(){
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    String contentType = (String)requireTagAttribute(WITH_VALUE_ATTRIBUTE, String.class, context);
    
    WebResponse response = getResponse(context);
    return response.getContentType().equals(contentType);
  }

}
