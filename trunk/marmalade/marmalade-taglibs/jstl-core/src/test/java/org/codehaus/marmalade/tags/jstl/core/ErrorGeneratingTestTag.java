/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class ErrorGeneratingTestTag extends AbstractMarmaladeTag{

  public ErrorGeneratingTestTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    throw new UnsupportedOperationException("Exception generated as part of a test.");
  }
}
