/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.AbstractOutputTag;
import org.codehaus.marmalade.MarmaladeExecutionContext;

/**
 * @author jdcasey
 */
public class ErrTag extends AbstractOutputTag {

  public ErrTag() {
  }

  protected void write(String message, MarmaladeExecutionContext context) {
    context.getErrWriter().print(message);
  }

}
