/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class IncludeTag extends IfTag {

  public IncludeTag(MarmaladeTagInfo tagInfo) {
      super(tagInfo);
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    context.getErrWriter().println(
      "WARNING: Use of <include/> tag is deprecated. Use <if/> instead."
    );
    
    super.doExecute(context);
  }

}
