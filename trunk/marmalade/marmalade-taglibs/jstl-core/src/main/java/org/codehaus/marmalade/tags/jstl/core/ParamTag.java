/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.generics.AbstractMarmaladeTag;

/**
 * @author jdcasey
 */
public class ParamTag extends AbstractMarmaladeTag {
  
  public static final String NAME_ATTRIBUTE = "name";
  public static final String VALUE_ATTRIBUTE = "value";

  public ParamTag() {
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    requireParent(UrlTag.class);
    
    String name = (String)requireTagAttribute(NAME_ATTRIBUTE, String.class, context);
    String value = (String)requireTagAttribute(VALUE_ATTRIBUTE, String.class, context);
    
    UrlTag parent = (UrlTag)getParent();
    parent.addParam(name, value);
  }

}
