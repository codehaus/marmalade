/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;

/**
 * @author jdcasey
 */
public class RemoveTag extends AbstractMarmaladeTag {
  
  public static final String VAR_ATTRIBUTE = "var";

  public RemoveTag() {
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    Object key = requireTagAttribute(VAR_ATTRIBUTE, context);
    context.removeVariable(key);
  }

}
