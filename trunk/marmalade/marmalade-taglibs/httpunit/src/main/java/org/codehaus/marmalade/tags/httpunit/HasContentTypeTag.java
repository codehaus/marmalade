/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class HasContentTypeTag extends AbstractAssertionTag{
  
  public static final String WITH_VALUE_ATTRIBUTE = "withValue";

  public HasContentTypeTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    String contentType = (String)requireTagAttribute(WITH_VALUE_ATTRIBUTE, String.class, context);
    
    WebResponse response = getResponse(context);
    return response.getContentType().equals(contentType);
  }

}
