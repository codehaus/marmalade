/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.form;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.form.*;
import org.codehaus.marmalade.tags.httpunit.structure.*;


/**
 * @author jdcasey
 */
public class FormHasActionTag extends AbstractFormSubAssertionTag{
  
  public static final String WITH_VALUE_ATTRIBUTE = "withValue";

  public FormHasActionTag(){
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    String value = (String)requireTagAttribute(WITH_VALUE_ATTRIBUTE, String.class, context);
    return getForm(context).getAction().equals(value);
  }

}
