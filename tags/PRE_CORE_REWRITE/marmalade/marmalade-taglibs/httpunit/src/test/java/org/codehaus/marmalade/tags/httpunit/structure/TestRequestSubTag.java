/* Created on Apr 26, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;

import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class TestRequestSubTag extends AbstractWebRequestSubTag{
  
  private boolean foundRequest = false;

  public TestRequestSubTag(){
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    foundRequest = getRequest(context) != null;
  }
  
  public boolean foundRequest() {
    return foundRequest;
  }
}
