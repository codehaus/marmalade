/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.AbstractConditionalTag;

/**
 * @author jdcasey
 */
public class WhenTag extends AbstractConditionalTag {

  /**
   * 
   */
  public WhenTag(MarmaladeTagInfo tagInfo) {
    super(tagInfo);
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    requireParent(ChooseTag.class);
    super.doExecute(context);
  }

}
