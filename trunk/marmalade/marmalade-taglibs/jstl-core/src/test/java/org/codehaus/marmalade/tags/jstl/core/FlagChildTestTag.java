/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class FlagChildTestTag extends AbstractMarmaladeTag{
  
  private boolean fired = false;

  public FlagChildTestTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }
  
  public boolean fired() {
    return fired;
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    fired = true;
  }
}
