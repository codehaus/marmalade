/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.generics.AbstractOutputTag;

/**
 * @author jdcasey
 */
public class OutTag extends AbstractOutputTag {

  public OutTag() {
  }

  protected void write(String message, MarmaladeExecutionContext context) {
    context.getOutWriter().print(message);
  }

}
