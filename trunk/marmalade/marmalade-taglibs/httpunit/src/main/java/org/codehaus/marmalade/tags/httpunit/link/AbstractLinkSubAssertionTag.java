/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.link;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
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
    DefaultHasLinkTag parent = (DefaultHasLinkTag)requireAncestor(DefaultHasLinkTag.class);
    return parent.getLink();
  }

}
