/* Created on Apr 20, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;


/**
 * @author jdcasey
 */
public class ScopeTag extends AbstractMarmaladeTag{

  public ScopeTag(){
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    context.newScope();
    try {
      processChildren(context);
    }
    finally {
      context.lastScope();
    }
  }
}
