/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractConditionalTag;

/**
 * @author jdcasey
 */
public class IncludeTag extends IfTag {

  public IncludeTag() {
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    context.getErrWriter().println(
      "WARNING: Use of <include/> tag is deprecated. Use <if/> instead."
    );
    
    super.doExecute(context);
  }

}
