/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.AbstractMarmaladeTag;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class OtherwiseTag extends AbstractMarmaladeTag {

  public OtherwiseTag() {
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    requireParent(ChooseTag.class);
    processChildren(context);
  }
}
