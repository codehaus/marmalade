/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractConditionalTag;

/**
 * @author jdcasey
 */
public class WhenTag extends AbstractConditionalTag {

  /**
   * 
   */
  public WhenTag() {
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    requireParent(ChooseTag.class);
    super.doExecute(context);
  }

}
