/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;


/**
 * @author jdcasey
 */
public class FlagChildTestTag extends AbstractMarmaladeTag{
  
  private boolean fired = false;

  public FlagChildTestTag(){
  }
  
  public boolean fired() {
    return fired;
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    fired = true;
  }
}
