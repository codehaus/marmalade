/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.util.XMLUtils;

/**
 * @author jdcasey
 */
public abstract class AbstractOutputTag extends AbstractMarmaladeTag {
  
  public static final String VALUE_ATTRIBUTE = "value";
  public static final String DEFAULT_ATTRIBUTE = "default";
  public static final String ESCAPE_XML_ATTRIBUTE = "escapeXml";

  /**
   */
  protected AbstractOutputTag(MarmaladeTagInfo tagInfo) {
    super(tagInfo);
  }
  
  protected abstract void write(String message, MarmaladeExecutionContext context)
  throws MarmaladeExecutionException;

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    String value = (String)getBody(context, String.class);
    
    MarmaladeAttributes attributes = getAttributes();
    if(value == null || value.length() < 1) {
      value = (String)attributes.getValue(VALUE_ATTRIBUTE, String.class, context);
    }
    
    if(value == null || value.length() < 1){
      value = (String)attributes.getValue(DEFAULT_ATTRIBUTE, String.class, context, "");
    }
    
    if(value == null || value.length() < 1) {
      throw new MarmaladeExecutionException(
        "Message is null. Either specify the value or default " +
        "attribute, or provide a non-null body for this tag."
      );
    }
    else {
      Boolean escapeXml = (Boolean)attributes.getValue(
        ESCAPE_XML_ATTRIBUTE, Boolean.class, context, Boolean.FALSE
      );
      
      boolean escape = false;
      if(escapeXml != null) {
        escape = escapeXml.booleanValue();
      }
      
      if(escape){
        value = XMLUtils.escapeXml(value);
      }
      
      write(value, context);
    }
  }

}
