/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.form;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class FormHasActionPatternTag extends AbstractFormSubAssertionTag{
  
  public static final String WITH_PATTERN_ATTRIBUTE = "withPattern";

  public FormHasActionPatternTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    String value = (String)requireTagAttribute(WITH_PATTERN_ATTRIBUTE, String.class, context);
    return getForm(context).getAction().matches(value);
  }

}
