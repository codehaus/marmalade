/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.link;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.AbstractAssertionTag;

import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;


/**
 * @author jdcasey
 */
public abstract class AbstractLinkSubAssertionTag extends AbstractAssertionTag{

  protected AbstractLinkSubAssertionTag(){
  }
  
  protected WebLink getLink(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    HasLinkTag parent = (HasLinkTag)requireAncestor(HasLinkTag.class);
    return parent.getLink();
  }

}
