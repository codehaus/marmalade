/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class HasTitlePatternTag extends AbstractAssertionTag{
  
  public static final String WITH_PATTERN_ATTRIBUTE = "withPattern";

  public HasTitlePatternTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    String value = (String)requireTagAttribute(WITH_PATTERN_ATTRIBUTE, String.class, context);
    WebResponse response = getResponse(context);
    try{
      return response.getTitle().matches(value);
    }
    catch(SAXException e){
      throw new MarmaladeExecutionException("Error parsing web response.", e);
    }
  }

}
