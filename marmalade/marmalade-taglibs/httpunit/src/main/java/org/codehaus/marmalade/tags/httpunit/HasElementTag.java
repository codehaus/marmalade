/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.xml.sax.SAXException;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class HasElementTag extends AbstractAssertionTag{
  
  public static final String WITH_ID_ATTRIBUTE = "withId";
  public static final String WITH_NAME_ATTRIBUTE = "withName";
  public static final String WITH_CLASSNAME_ATTRIBUTE = "withClassName";
  public static final String WITH_TITLE_ATTRIBUTE = "withTitle";
  
  public HasElementTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    WebResponse response = getResponse(context);
    
    MarmaladeAttributes attributes = getAttributes();
    
    String id = (String)attributes.getValue(WITH_ID_ATTRIBUTE, String.class, context);
    boolean result = false;
    if(id != null && id.length() > 0) {
      try{
        HTMLElement element = response.getElementWithID(id);
        result = testElement(element, context, attributes, true);
      }
      catch(SAXException e){
        throw new MarmaladeExecutionException("Error parsing web response.", e);
      }
    }
    else {
      String name = (String)attributes.getValue(WITH_NAME_ATTRIBUTE, String.class, context);
      if(name != null && name.length() > 0) {
        try{
          HTMLElement[] elements = response.getElementsWithName(name);
          for(int i = 0; i < elements.length; i++){
            HTMLElement element = elements[i];
            result = testElement(element, context, attributes, false);
            if(result) {
              break;
            }
          }
        }
        catch(SAXException e){
          throw new MarmaladeExecutionException("Error parsing web response.", e);
        }
      }
    }
    
    return result;
  }

  private boolean testElement(HTMLElement element, MarmaladeExecutionContext context, 
                              MarmaladeAttributes attributes, boolean testName)
  throws MarmaladeExecutionException
  {
    boolean result = true;
    if(element == null) {
      result = false;
    }
    else {
      String className = (String)attributes.getValue(WITH_CLASSNAME_ATTRIBUTE, String.class, context);
      if(className != null && className.length() > 0) {
        String eClass = element.getClassName();
        if(eClass == null || eClass.length() < 1 || !eClass.equals(className)) {
          result = false;
        }
      }
      
      String title = (String)attributes.getValue(WITH_TITLE_ATTRIBUTE, String.class, context);
      if(title != null && title.length() > 0) {
        String eTitle = element.getTitle();
        if(eTitle == null || eTitle.length() < 1 || !eTitle.equals(title)) {result = false;}
      }
      
      if(testName) {
        String name = (String)attributes.getValue(WITH_NAME_ATTRIBUTE, String.class, context);
        if(name != null && name.length() > 0) {
          String eName = element.getName();
          if(eName == null || eName.length() < 1 || !eName.equals(name)) {result = false;}
        }
      }
    }
    
    return result;
  }

}
