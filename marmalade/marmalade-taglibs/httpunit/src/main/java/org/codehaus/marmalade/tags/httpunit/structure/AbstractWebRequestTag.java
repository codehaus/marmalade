/* Created on Apr 21, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import com.meterware.httpunit.WebRequest;


/**
 * @author jdcasey
 */
public abstract class AbstractWebRequestTag extends AbstractWebConversationSubTag
                                            implements HeaderParent
{
  
  public static final String VAR_ATTRIBUTE = "var";
  
  public static final String URL_ATTRIBUTE = "url";
  public static final String BASE_URL_ATTRIBUTE = "baseUrl";
  public static final String TARGET_ATTRIBUTE = "target";
  
  private WebRequest request;

  protected AbstractWebRequestTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    MarmaladeAttributes attrs = getAttributes();
    
    String url = (String)requireTagAttribute(URL_ATTRIBUTE, String.class, context);
    String target = (String)attrs.getValue(TARGET_ATTRIBUTE, String.class, context);
    Object base = attrs.getValue(BASE_URL_ATTRIBUTE, context);
    URL baseUrl = null;
    if(base != null) {
      if(base instanceof String) {
        try{
          baseUrl = new URL((String)base);
        }
        catch(MalformedURLException e){
          throw new MarmaladeExecutionException("Error instantiating base URL.", e);
        }
      }
      else {
        baseUrl = (URL)base;
      }
    }
    
    request = createRequest(baseUrl, url, target);
    String var = (String)attrs.getValue(VAR_ATTRIBUTE, String.class, context);
    if(var != null && var.length() > 0) {
      context.setVariable(var, request);
    }
  }
  
  protected WebRequest getRequest() {
    return request;
  }
  
  public void setHeader(String name, String value) {
    request.setHeaderField(name, value);
  }
  
  public void setParameter(String name, String value) {
    request.setParameter(name, value);
  }
  
  protected abstract WebRequest createRequest(URL baseUrl, String url, String target)
  throws MarmaladeExecutionException;
  
  protected void doReset(){
    this.request = null;
    super.doReset();
  }
}
