/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.form;

import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class FormHasActionTag extends AbstractFormSubAssertionTag{
  
  public static final String WITH_VALUE_ATTRIBUTE = "withValue";

  public FormHasActionTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    String value = (String)requireTagAttribute(WITH_VALUE_ATTRIBUTE, String.class, context);
    return getForm(context).getAction().equals(value);
  }

}
