/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.form;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.AbstractAssertionTag;
import org.xml.sax.SAXException;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class DefaultHasFormTag extends AbstractAssertionTag{
  
  public static final String WITH_ID_ATTRIBUTE = "withId";
  public static final String WITH_NAME_ATTRIBUTE = "withName";
  
  private WebForm form;
  
  public DefaultHasFormTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }
  
  public WebForm getForm() {
    return form;
  }

  protected void setUp(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    WebResponse response = getResponse(context);
    
    MarmaladeAttributes attributes = getAttributes();
    
    String id = (String)attributes.getValue(WITH_ID_ATTRIBUTE, String.class, context);
    if(id != null && id.length() > 0) {
      try{
        form = response.getFormWithID(id);
      }
      catch(SAXException e){
        throw new MarmaladeExecutionException("Error parsing web response.", e);
      }
    }
    
    if(form == null) {
      String name = (String)attributes.getValue(WITH_NAME_ATTRIBUTE, String.class, context);
      if(name != null && name.length() > 0) {
        try{
          form = response.getFormWithName(name);
        }
        catch(SAXException e){
          throw new MarmaladeExecutionException("Error parsing web response.", e);
        }
      }
    }
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    return form != null;
  }

}
