/* Created on Apr 21, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import java.net.URL;

import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.structure.*;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;


/**
 * @author jdcasey
 */
public class GetRequestTag extends AbstractWebRequestTag{

  public GetRequestTag(){
  }

  protected WebRequest createRequest(URL baseUrl, String url, String target)
  throws MarmaladeExecutionException
  {
    if(baseUrl != null) {
      if(target == null || target.length() < 1) {
        return new GetMethodWebRequest(baseUrl, url);
      }
      else {
        return new GetMethodWebRequest(baseUrl, url, target);
      }
    }
    else {
      return new GetMethodWebRequest(url);
    }
  }

}
