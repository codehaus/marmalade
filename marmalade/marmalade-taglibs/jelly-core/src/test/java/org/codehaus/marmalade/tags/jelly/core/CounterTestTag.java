/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;


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
