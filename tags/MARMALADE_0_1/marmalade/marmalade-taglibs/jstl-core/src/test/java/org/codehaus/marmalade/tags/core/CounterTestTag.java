/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.tags.core;

import org.codehaus.marmalade.AbstractMarmaladeTag;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;


/**
 * @author jdcasey
 */
public class CounterTestTag extends AbstractMarmaladeTag{
  
  private int counter = 0;

  public CounterTestTag(){
  }
  
  public int counter() {
    return counter;
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    counter++;
  }
}
