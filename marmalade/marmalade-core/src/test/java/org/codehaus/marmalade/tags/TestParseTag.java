/* Created on May 25, 2004 */
package org.codehaus.marmalade.tags;

import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;


/**
 * @author jdcasey
 */
public class TestParseTag extends AbstractOutputTag{

  /**
   * @param tagInfo
   */
  public TestParseTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected void write(String message, MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    context.getOutWriter().print(message);
  }

}
