/* Created on Apr 21, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class AbstractWebResponseSubTag extends AbstractWebRequestSubTag implements WebResponseSubTag{
  
  public static final String RESPONSE_ATTRIBUTE = "response";

  protected AbstractWebResponseSubTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  public WebResponse getResponse(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    MarmaladeAttributes attrs = getAttributes();
    
    WebResponse response = (WebResponse)attrs.getValue(
      RESPONSE_ATTRIBUTE, WebResponse.class, context
    );
    
    if(response == null) {
      DefaultWebResponseTag ancestor = (DefaultWebResponseTag)requireAncestor(
        DefaultWebResponseTag.class
      );
      response = ancestor.getResponse();
    }
    
    return response;
  }

}
