/* Created on Apr 21, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import java.net.URL;

import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.structure.*;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;


/**
 * @author jdcasey
 */
public class PostRequestTag extends AbstractWebRequestTag{

  public PostRequestTag(){
  }

  protected WebRequest createRequest(URL baseUrl, String url, String target)
  throws MarmaladeExecutionException
  {
    PostMethodWebRequest request = null;
    if(baseUrl != null) {
      if(target == null || target.length() < 1) {
        request = new PostMethodWebRequest(baseUrl.toExternalForm() + url);
      }
      else {
        request = new PostMethodWebRequest(baseUrl, url, target);
      }
    }
    else {
      request = new PostMethodWebRequest(url);
    }
    
    return request;
  }

}