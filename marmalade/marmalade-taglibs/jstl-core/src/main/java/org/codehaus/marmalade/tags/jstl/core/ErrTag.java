/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.tags.AbstractOutputTag;

/**
 * @author jdcasey
 */
public class ErrTag extends AbstractOutputTag {

  public ErrTag(MarmaladeTagInfo tagInfo) {
    super(tagInfo);
  }

  protected void write(String message, MarmaladeExecutionContext context) {
    context.getErrWriter().print(message);
  }

}
