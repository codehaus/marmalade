/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.link;

import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.AbstractAssertionTag;

import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;


/**
 * @author jdcasey
 */
public abstract class AbstractLinkSubAssertionTag extends AbstractAssertionTag{

  protected AbstractLinkSubAssertionTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }
  
  protected WebLink getLink(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    HasLinkTag parent = (HasLinkTag)requireAncestor(HasLinkTag.class);
    return parent.getLink();
  }

}
