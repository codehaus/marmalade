/* Created on Apr 26, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;

import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class TestResponseSubTag extends AbstractWebResponseSubTag{
  
  private boolean foundResponse = false;

  public TestResponseSubTag(){
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    WebResponse response = getResponse(context);
    foundResponse = response != null;
  }
  
  public boolean foundResponse() {
    return foundResponse;
  }
}
