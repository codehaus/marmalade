/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.AbstractConditionalTag;

/**
 * @author jdcasey
 */
public class IfTag extends AbstractConditionalTag {

  public IfTag(MarmaladeTagInfo tagInfo) {
      super(tagInfo);
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
