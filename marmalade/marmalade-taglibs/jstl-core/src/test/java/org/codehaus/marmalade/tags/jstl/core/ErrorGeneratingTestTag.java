/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.AbstractMarmaladeTag;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;


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
