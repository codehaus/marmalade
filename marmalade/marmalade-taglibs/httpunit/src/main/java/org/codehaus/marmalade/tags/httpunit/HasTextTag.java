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
public class HasTextTag extends AbstractAssertionTag{
  
  public static final String WITH_VALUE_ATTRIBUTE = "withValue";

  public HasTextTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    String value = (String)requireTagAttribute(WITH_VALUE_ATTRIBUTE, String.class, context);
    WebResponse response = getResponse(context);
    try{
      return response.getText().indexOf(value) > -1;
    }
    catch(IOException e){
      throw new MarmaladeExecutionException("Error reading web response.", e);
    }
  }

}
