/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import java.io.IOException;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.structure.*;

import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class IsHtmlTag extends AbstractAssertionTag{
  
  public IsHtmlTag(){
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    WebResponse response = getResponse(context);
    return response.isHTML();
  }

}
