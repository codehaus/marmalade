/* Created on Apr 21, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import com.meterware.httpunit.WebConversation;


/**
 * @author jdcasey
 */
public class AbstractWebConversationSubTag extends AbstractMarmaladeTag implements WebConversationSubTag{

  public static final String CONVERSATION_ATTRIBUTE = "conversation";
  
  public AbstractWebConversationSubTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }
  
  public WebConversation getConversation(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    MarmaladeAttributes attrs = getAttributes();
    
    WebConversation conversation = (WebConversation)attrs.getValue(
      CONVERSATION_ATTRIBUTE, WebConversation.class, context
    );
    
    if(conversation == null) {
      DefaultWebConversationTag ancestor = (DefaultWebConversationTag)requireAncestor(DefaultWebConversationTag.class);
      conversation = ancestor.getConversation();
    }
    
    return conversation;
  }

}
