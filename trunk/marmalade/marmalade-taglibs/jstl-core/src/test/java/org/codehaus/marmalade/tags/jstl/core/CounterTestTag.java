/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;


/**
 * @author jdcasey
 */
public class CounterTestTag extends AbstractMarmaladeTag{
  
  private int counter = 0;

  public CounterTestTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }
  
  public int counter() {
    return counter;
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    counter++;
  }
}
