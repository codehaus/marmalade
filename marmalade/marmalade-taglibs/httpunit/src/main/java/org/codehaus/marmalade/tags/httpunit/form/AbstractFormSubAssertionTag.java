/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.form;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.AbstractAssertionTag;

import com.meterware.httpunit.WebForm;


/**
 * @author jdcasey
 */
public abstract class AbstractFormSubAssertionTag extends AbstractAssertionTag{

  protected AbstractFormSubAssertionTag(){
  }
  
  protected WebForm getForm(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    HasFormTag parent = (HasFormTag)requireAncestor(HasFormTag.class);
    return parent.getForm();
  }

}
