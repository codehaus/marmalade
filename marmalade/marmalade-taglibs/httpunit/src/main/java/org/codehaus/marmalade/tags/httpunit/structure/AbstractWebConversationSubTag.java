/* Created on Apr 21, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import org.codehaus.marmalade.MarmaladeAttributes;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;

import com.meterware.httpunit.WebConversation;


/**
 * @author jdcasey
 */
public class AbstractWebConversationSubTag extends AbstractMarmaladeTag{

  public static final String CONVERSATION_ATTRIBUTE = "conversation";
  
  public AbstractWebConversationSubTag(){
  }
  
  protected WebConversation getConversation(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    MarmaladeAttributes attrs = getAttributes();
    
    WebConversation conversation = (WebConversation)attrs.getValue(
      CONVERSATION_ATTRIBUTE, WebConversation.class, context
    );
    
    if(conversation == null) {
      WebConversationTag ancestor = (WebConversationTag)requireAncestor(WebConversationTag.class);
      conversation = ancestor.getConversation();
    }
    
    return conversation;
  }

}
