/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class RemoveTag extends AbstractMarmaladeTag {
  
  public static final String VAR_ATTRIBUTE = "var";

  public RemoveTag(MarmaladeTagInfo tagInfo) {
    super(tagInfo);
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    Object key = requireTagAttribute(VAR_ATTRIBUTE, context);
    context.removeVariable(key);
  }

}
