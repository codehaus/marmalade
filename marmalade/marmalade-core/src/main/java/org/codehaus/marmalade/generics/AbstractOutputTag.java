/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.generics;

import org.codehaus.marmalade.MarmaladeAttributes;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
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
  protected AbstractOutputTag() {
  }
  
  protected abstract void write(String message, MarmaladeExecutionContext context);

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    MarmaladeAttributes attributes = getAttributes();
    String value = (String)attributes.getValue(VALUE_ATTRIBUTE, String.class, context);
    if(value == null || value.length() < 1){
      value = (String)attributes.getValue(DEFAULT_ATTRIBUTE, String.class, context, "");
    }
    
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
