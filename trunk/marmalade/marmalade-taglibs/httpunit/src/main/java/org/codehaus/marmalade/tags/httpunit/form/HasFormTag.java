/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.form;

import org.codehaus.marmalade.MarmaladeAttributes;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.AbstractAssertionTag;
import org.codehaus.marmalade.tags.httpunit.structure.*;
import org.xml.sax.SAXException;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class HasFormTag extends AbstractAssertionTag{
  
  public static final String WITH_ID_ATTRIBUTE = "withId";
  public static final String WITH_NAME_ATTRIBUTE = "withName";
  
  private WebForm form;
  
  public HasFormTag(){
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
