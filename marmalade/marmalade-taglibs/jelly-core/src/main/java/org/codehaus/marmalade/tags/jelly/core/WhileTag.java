/* Created on Apr 20, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.AbstractConditionalTag;

/**
 * @author jdcasey
 */
public class WhileTag extends AbstractConditionalTag{

  public WhileTag(MarmaladeTagInfo tagInfo){
      super(tagInfo);
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    while(conditionMatches(context)) {
      processChildren(context);
    }
  }

  protected boolean alwaysProcessChildren(){
    return false;
  }
}
