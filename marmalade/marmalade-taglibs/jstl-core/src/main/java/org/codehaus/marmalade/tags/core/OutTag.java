/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.core;

import org.codehaus.marmalade.AbstractOutputTag;
import org.codehaus.marmalade.MarmaladeExecutionContext;

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