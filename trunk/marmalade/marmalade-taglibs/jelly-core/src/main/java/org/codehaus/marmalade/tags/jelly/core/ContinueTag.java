/* Created on Apr 18, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.LoopingTag;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractLoopControlTag;


/**
 * @author jdcasey
 */
public class ContinueTag extends AbstractLoopControlTag{

  public ContinueTag(){
  }

  protected void modifyLoop(MarmaladeExecutionContext context, LoopingTag parent)
  throws MarmaladeExecutionException
  {
    parent.continueLoop();
  }

}
