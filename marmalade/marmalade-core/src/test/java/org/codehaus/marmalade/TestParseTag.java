/* Created on Apr 12, 2004 */
package org.codehaus.marmalade;

import org.codehaus.marmalade.generics.*;


/**
 * @author jdcasey
 */
public class TestParseTag extends AbstractMarmaladeTag{

  public TestParseTag(){
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    context.getOutWriter().print(requireTagAttribute("output", context));
  }
}
