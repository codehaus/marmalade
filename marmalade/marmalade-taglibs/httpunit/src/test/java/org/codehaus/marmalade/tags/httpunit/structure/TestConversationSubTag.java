/* Created on Apr 29, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;

import com.meterware.httpunit.WebConversation;


/**
 * @author jdcasey
 */
public class TestConversationSubTag extends AbstractWebConversationSubTag{

  public TestConversationSubTag(){
  }
  
  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    WebConversation conversation = getConversation(context);
    context.setVariable("testresult", conversation);
  }
}
