/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;


/**
 * @author jdcasey
 */
public class ErrorGeneratingTestTag extends AbstractMarmaladeTag{

  public ErrorGeneratingTestTag(){
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    throw new UnsupportedOperationException("Exception generated as part of a test.");
  }
}