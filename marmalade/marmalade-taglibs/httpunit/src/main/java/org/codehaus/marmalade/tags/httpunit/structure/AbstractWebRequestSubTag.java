/* Created on Apr 21, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import com.meterware.httpunit.WebRequest;


/**
 * @author jdcasey
 */
public class AbstractWebRequestSubTag extends AbstractWebConversationSubTag{
  
  public static final String REQUEST_ATTRIBUTE = "request";

  protected AbstractWebRequestSubTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected WebRequest getRequest(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    MarmaladeAttributes attrs = getAttributes();
    
    WebRequest request = (WebRequest)attrs.getValue(
      REQUEST_ATTRIBUTE, WebRequest.class, context
    );
    
    if(request == null) {
      AbstractWebRequestTag ancestor = (AbstractWebRequestTag)requireAncestor(
        AbstractWebRequestTag.class
      );
      request = ancestor.getRequest();
    }
    
    return request;
  }

}
