/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.AbstractConditionalTag;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class IfTag extends AbstractConditionalTag {

  public IfTag() {
  }

  protected boolean alwaysProcessChildren() {
    return false;
  }
  
  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    boolean match = conditionMatches(context);
    if(match){
      processChildren(context);
    }
  }

}
