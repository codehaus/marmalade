/* Created on Apr 18, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.AbstractLoopControlTag;
import org.codehaus.marmalade.tags.LoopingTag;

/**
 * @author jdcasey
 */
public class BreakTag extends AbstractLoopControlTag{

  public BreakTag(MarmaladeTagInfo tagInfo){
      super(tagInfo);
  }

  protected void modifyLoop(MarmaladeExecutionContext context, LoopingTag parent)
  throws MarmaladeExecutionException
  {
    parent.breakLoop();
  }

}
