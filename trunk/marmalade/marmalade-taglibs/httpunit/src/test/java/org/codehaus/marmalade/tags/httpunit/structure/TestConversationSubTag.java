/* Created on Apr 29, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import com.meterware.httpunit.WebConversation;


/**
 * @author jdcasey
 */
public class TestConversationSubTag extends AbstractWebConversationSubTag{

  public TestConversationSubTag(MarmaladeTagInfo mti){
    super(mti);
  }
  
  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    WebConversation conversation = getConversation(context);
    context.setVariable("testresult", conversation);
  }
}
