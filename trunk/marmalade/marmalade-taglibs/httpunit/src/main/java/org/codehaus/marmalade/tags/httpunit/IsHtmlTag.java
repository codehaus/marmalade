/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import java.io.IOException;

import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class IsHtmlTag extends AbstractAssertionTag{
  
  public IsHtmlTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    WebResponse response = getResponse(context);
    return response.isHTML();
  }

}
