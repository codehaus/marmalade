/* Created on Apr 21, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import org.codehaus.marmalade.MarmaladeAttributes;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;

import com.meterware.httpunit.DialogResponder;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebWindow;


/**
 * @author jdcasey
 */
public class WebConversationTag extends AbstractMarmaladeTag implements HeaderParent{
  
  public static final String VAR_ATTRIBUTE = "var";

  public static final String USER_ATTRIBUTE = "user";
  public static final String PASSWORD_ATTRIBUTE = "password";
  public static final String FAIL_ON_ERROR_ATTRIBUTE = "failOnError";
  public static final String MAIN_WINDOW_ATTRIBUTE = "mainWindow";
  public static final String DIALOG_RESPONDER_ATTRIBUTE = "dialogResponder";
  
  private static final Integer DEFAULT_WEB_PORT = new Integer(80);
  private static final Boolean DEFAULT_FAIL_ON_ERROR = Boolean.TRUE;
  
  private WebConversation conversation;
  
  public WebConversationTag(){
  }
  
  public WebConversation getConversation() {
    return conversation;
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    conversation = new WebConversation();
    
    MarmaladeAttributes attrs = getAttributes();
    String user = (String)attrs.getValue(USER_ATTRIBUTE, String.class, context);
    String password = (String)attrs.getValue(PASSWORD_ATTRIBUTE, String.class, context, "");
    
    if(user != null && user.length() > 0) {
      conversation.setAuthorization(user, password);
    }
    
    Boolean failOnError = (Boolean)attrs.getValue(
      FAIL_ON_ERROR_ATTRIBUTE, Boolean.class, context, DEFAULT_FAIL_ON_ERROR
    );
    if(failOnError != null) {
      conversation.setExceptionsThrownOnErrorStatus(failOnError.booleanValue());
    }
    
    WebWindow window = (WebWindow)attrs.getValue(MAIN_WINDOW_ATTRIBUTE, WebWindow.class, context);
    if(window != null) {
      conversation.setMainWindow(window);
    }
    
    DialogResponder dlgResp = (DialogResponder)attrs.getValue(
      DIALOG_RESPONDER_ATTRIBUTE, DialogResponder.class, context
    );
    
    if(dlgResp != null) {
      conversation.setDialogResponder(dlgResp);
    }
    
    String var = (String)attrs.getValue(VAR_ATTRIBUTE, String.class, context);
    if(var != null && var.length() > 0) {
      context.setVariable(var, conversation);
    }
  }

  protected void doReset(){
    this.conversation = null;
    super.doReset();
  }

  public void setHeader(String name, String value){
    this.conversation.setHeaderField(name, value);
  }
}
