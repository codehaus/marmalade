/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.form;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.AbstractAssertionTag;

import com.meterware.httpunit.WebForm;


/**
 * @author jdcasey
 */
public abstract class AbstractFormSubAssertionTag extends AbstractAssertionTag{

  protected AbstractFormSubAssertionTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }
  
  protected WebForm getForm(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    DefaultHasFormTag parent = (DefaultHasFormTag)requireAncestor(DefaultHasFormTag.class);
    return parent.getForm();
  }

}
