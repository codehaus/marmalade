/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class HeaderTag extends AbstractMarmaladeTag{

  public static final String NAME_ATTRIBUTE = "name";
  public static final String VALUE_ATTRIBUTE = "value";

  public HeaderTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    String name = (String)requireTagAttribute(NAME_ATTRIBUTE, String.class, context);
    
    String value = (String)getAttributes().getValue(VALUE_ATTRIBUTE, String.class, context);
    if(value == null || value.length() < 1) {
      value = (String)getBody(context, String.class);
    }
    
    HeaderParent parent = (HeaderParent)requireParent(HeaderParent.class);
    parent.setHeader(name, value);
  }
}
