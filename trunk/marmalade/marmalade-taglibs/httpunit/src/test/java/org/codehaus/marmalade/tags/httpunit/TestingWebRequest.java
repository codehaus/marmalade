/* Created on Apr 23, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import java.net.MalformedURLException;
import java.net.URL;

import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebRequestSource;


/**
 * @author jdcasey
 */
public class TestingWebRequest extends WebRequest{

  public TestingWebRequest(){
    super((String)null);
  }

  public String getMethod(){
    return null;
  }

}
