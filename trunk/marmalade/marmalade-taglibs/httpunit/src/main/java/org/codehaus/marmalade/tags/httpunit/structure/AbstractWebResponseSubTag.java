/* Created on Apr 21, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import org.codehaus.marmalade.MarmaladeAttributes;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;

import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class AbstractWebResponseSubTag extends AbstractWebRequestSubTag{
  
  public static final String RESPONSE_ATTRIBUTE = "response";

  public AbstractWebResponseSubTag(){
  }

  protected WebResponse getResponse(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    MarmaladeAttributes attrs = getAttributes();
    
    WebResponse response = (WebResponse)attrs.getValue(
      RESPONSE_ATTRIBUTE, WebResponse.class, context
    );
    
    if(response == null) {
      WebResponseTag ancestor = (WebResponseTag)requireAncestor(
        WebResponseTag.class
      );
      response = ancestor.getResponse();
    }
    
    return response;
  }

}
