/* Created on Apr 21, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import java.io.IOException;
import java.net.MalformedURLException;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class DefaultWebResponseTag extends AbstractWebRequestSubTag
    implements WebResponseTag
{
  
  public static final String VAR_ATTRIBUTE = "var";
  
  private WebResponse response;
  
  public DefaultWebResponseTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }
  
  public WebResponse getResponse() {
    return response;
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    WebConversation conversation = getConversation(context);
    WebRequest request = getRequest(context);
    
    try{
      response = conversation.getResponse(request);
      String var = (String)getAttributes().getValue(VAR_ATTRIBUTE, String.class, context);
      if(var != null && var.length() > 0) {
        context.setVariable(var, response);
      }
    }
    catch(MalformedURLException e){
      throw new MarmaladeExecutionException("Error sending request.", e);
    }
    catch(IOException e){
      throw new MarmaladeExecutionException("Error executing web request.", e);
    }
    catch(SAXException e){
      throw new MarmaladeExecutionException("Error parsing response.", e);
    }
  }

  protected void doReset(){
    this.response = null;
    super.doReset();
  }
}
