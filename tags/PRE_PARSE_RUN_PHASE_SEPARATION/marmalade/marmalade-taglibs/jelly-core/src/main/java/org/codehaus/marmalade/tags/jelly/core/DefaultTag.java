/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;

/**
 * @author jdcasey
 */
public class DefaultTag extends AbstractMarmaladeTag {

  public DefaultTag() {
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    requireParent(SwitchTag.class);
    processChildren(context);
  }
}
