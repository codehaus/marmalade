/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.link;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.AbstractAssertionTag;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class DefaultHasLinkTag extends AbstractAssertionTag{
  
  public static final String WITH_ID_ATTRIBUTE = "withId";
  public static final String WITH_NAME_ATTRIBUTE = "withName";
  public static final String WITH_TEXT_ATTRIBUTE = "withText";
  public static final String WITH_IMAGE_TEXT_ATTRIBUTE = "withImageText";
  
  private WebLink link;
  
  public DefaultHasLinkTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected void setUp(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    WebResponse response = getResponse(context);
    
    MarmaladeAttributes attributes = getAttributes();
    
    String id = (String)attributes.getValue(WITH_ID_ATTRIBUTE, String.class, context);
    if(id != null && id.length() > 0) {
      try{
        link = response.getLinkWithID(id);
      }
      catch(SAXException e){
        throw new MarmaladeExecutionException("Error parsing web response.", e);
      }
    }
    
    if(link == null) {
      String name = (String)attributes.getValue(WITH_NAME_ATTRIBUTE, String.class, context);
      if(name != null && name.length() > 0) {
        try{
          link = response.getLinkWithName(name);
        }
        catch(SAXException e){
          throw new MarmaladeExecutionException("Error parsing web response.", e);
        }
      }
    }
    
    if(link == null) {
      String text = (String)attributes.getValue(WITH_TEXT_ATTRIBUTE, String.class, context);
      if(text != null && text.length() > 0) {
        try{
          link = response.getLinkWith(text);
        }
        catch(SAXException e){
          throw new MarmaladeExecutionException("Error parsing web response.", e);
        }
      }
    }
    
    if(link == null) {
      String imgText = (String)attributes.getValue(WITH_IMAGE_TEXT_ATTRIBUTE, String.class, context);
      if(imgText != null && imgText.length() > 0) {
        try{
          link = response.getLinkWithImageText(imgText);
        }
        catch(SAXException e){
          throw new MarmaladeExecutionException("Error parsing web response.", e);
        }
      }
    }
  }

  public WebLink getLink(){
    return link;
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    return link != null;
  }

}
