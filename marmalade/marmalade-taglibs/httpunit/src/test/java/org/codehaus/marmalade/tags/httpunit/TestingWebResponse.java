/* Created on Apr 23, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import java.net.URL;

import com.meterware.httpunit.WebClient;
import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class TestingWebResponse extends WebResponse{

  public TestingWebResponse(){
    super(null, null, null);
  }

  public int getResponseCode(){
    return 0;
  }

  public String getResponseMessage(){
    return null;
  }

  public String[] getHeaderFieldNames(){
    return null;
  }

  public String getHeaderField(String arg0){
    return null;
  }

  public String toString(){
    return null;
  }

  public String[] getHeaderFields(String arg0){
    return null;
  }

}
