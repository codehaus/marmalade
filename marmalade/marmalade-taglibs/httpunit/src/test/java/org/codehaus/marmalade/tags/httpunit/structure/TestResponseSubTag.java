/* Created on Apr 26, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class TestResponseSubTag extends AbstractWebResponseSubTag{
  
  private boolean foundResponse = false;

  public TestResponseSubTag(MarmaladeTagInfo mti){
    super(mti);
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    WebResponse response = getResponse(context);
    foundResponse = response != null;
  }
  
  public boolean foundResponse() {
    return foundResponse;
  }
}
