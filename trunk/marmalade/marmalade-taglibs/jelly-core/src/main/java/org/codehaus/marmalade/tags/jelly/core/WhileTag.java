/* Created on Apr 20, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractConditionalTag;


/**
 * @author jdcasey
 */
public class WhileTag extends AbstractConditionalTag{

  public WhileTag(){
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
