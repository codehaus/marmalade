/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import java.io.IOException;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.structure.*;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class HasTitleTag extends AbstractAssertionTag{
  
  public static final String WITH_VALUE_ATTRIBUTE = "withValue";

  public HasTitleTag(){
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    String value = (String)requireTagAttribute(WITH_VALUE_ATTRIBUTE, String.class, context);
    WebResponse response = getResponse(context);
    try{
      return response.getTitle().indexOf(value) > -1;
    }
    catch(SAXException e){
      throw new MarmaladeExecutionException("Error parsing title from web response.", e);
    }
  }

}
