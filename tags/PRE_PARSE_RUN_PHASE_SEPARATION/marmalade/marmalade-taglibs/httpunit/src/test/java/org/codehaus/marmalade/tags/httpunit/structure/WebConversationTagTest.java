/* Created on Apr 28, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.defaults.DefaultContext;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.testing.AbstractTagCGLibTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.cglib.Mock;

import com.meterware.httpunit.DialogResponder;
import com.meterware.httpunit.WebConversation;


/**
 * @author jdcasey
 */
public class WebConversationTagTest extends AbstractTagCGLibTestCase{
  
  public void testEmbeddedSuccess_NoAttributes() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock attrMock = attributesEmpty();
    Mock childAttrMock = attributesEmpty();
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    TestConversationSubTag child = new TestConversationSubTag();
    
    child.setParent(tag);
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    tag.child(child);
    
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertNotNull("conversation should be bound to context.", (WebConversation)ctx.getVariable("testresult", null));
    
    attrMock.verify();
    childAttrMock.verify();
  }

  public void testEmbeddedSuccess_UserPassword() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.USER_ATTRIBUTE, "testEmbeddedusr");
    attributes.put(WebConversationTag.PASSWORD_ATTRIBUTE, "testEmbeddedpass");
    
    Mock attrMock = attributesFromMap(attributes);
    
    Mock childAttrMock = attributesEmpty();
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    TestConversationSubTag child = new TestConversationSubTag();
    
    child.setParent(tag);
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    tag.child(child);
    
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable("testresult", null);
    assertNotNull("conversation should be bound to context.", conv);
    
    attrMock.verify();
    childAttrMock.verify();
  }

  public void testEmbeddedSuccess_UserNoPassword() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.USER_ATTRIBUTE, "testEmbeddedusr");
    
    Mock attrMock = attributesFromMap(attributes);
    
    Mock childAttrMock = attributesEmpty();
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    TestConversationSubTag child = new TestConversationSubTag();
    
    child.setParent(tag);
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    tag.child(child);
    
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable("testresult", null);
    assertNotNull("conversation should be bound to context.", conv);
    
    attrMock.verify();
    childAttrMock.verify();
  }

  public void testEmbeddedSuccess_NoUserPassword() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.PASSWORD_ATTRIBUTE, "testEmbeddedpass");
    
    Mock attrMock = attributesFromMap(attributes);
    
    Mock childAttrMock = attributesEmpty();
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    TestConversationSubTag child = new TestConversationSubTag();
    
    child.setParent(tag);
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    tag.child(child);
    
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable("testresult", null);
    assertNotNull("conversation should be bound to context.", conv);
    
    attrMock.verify();
    childAttrMock.verify();
  }
  
  public void testEmbeddedSuccess_FailOnError() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.FAIL_ON_ERROR_ATTRIBUTE, "true");
    
    Mock attrMock = attributesFromMap(attributes);
    
    Mock childAttrMock = attributesEmpty();
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    TestConversationSubTag child = new TestConversationSubTag();
    
    child.setParent(tag);
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    tag.child(child);
    
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable("testresult", null);
    assertNotNull("conversation should be bound to context.", conv);
    assertTrue("Conversation should be set to fail on error", conv.getExceptionsThrownOnErrorStatus());
    
    attrMock.verify();
    childAttrMock.verify();
  }
  
  public void testEmbeddedFail_BadFailOnError() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.FAIL_ON_ERROR_ATTRIBUTE, "invalid");
    
    Mock attrMock = attributesFromMap(attributes);
    
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("Should have throw an expression error for the bad failOnError attribute value");
    }
    catch(ExpressionEvaluationException e) {}
    
    attrMock.verify();
  }
  
  public void testEmbeddedSuccess_DialogResponder() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.DIALOG_RESPONDER_ATTRIBUTE, "#reponder");
    
    Mock attrMock = attributesFromMap(attributes);
    
    Mock childAttrMock = attributesEmpty();
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    TestConversationSubTag child = new TestConversationSubTag();
    
    child.setParent(tag);
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    tag.child(child);
    
    tag.end("conversation");
    
    Mock dlgRespMock = new Mock(DialogResponder.class);
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("responder", (DialogResponder)dlgRespMock.proxy());
    
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable("testresult", null);
    assertNotNull("conversation should be bound to context.", conv);
    
    System.out.println(
      "Warning!!! Cannot properly verify that the dialog responder was set. " +
      "API does not provide the means."
    );
    
    attrMock.verify();
    childAttrMock.verify();
    dlgRespMock.verify();
  }
  
/* 4/29/2004: Commenting out, because HTTPUnit's proxy settings are currently global to the VM.
 * Therefore, we should supply another tag to create a WebClient and use that to config a global
 * proxy setting if that's what we need to do. This will make the global nature of the proxy setting
 * more intuitive.
  public void testEmbeddedSuccess_ProxyHostPort() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.PROXY_HOST_ATTRIBUTE, "proxy.net");
    attributes.put(WebConversationTag.PROXY_PORT_ATTRIBUTE, "80");
    
    Mock attrMock = attributesFromMap(attributes);
    
    Mock childAttrMock = attributesEmpty();
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    TestConversationSubTag child = new TestConversationSubTag();
    
    child.setParent(tag);
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    tag.child(child);
    
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable("testresult", null);
    assertNotNull("conversation should be bound to context.", conv);
    assertEquals("Conversation proxy host is wrong.", "proxy.net", conv.getProxyHost());
    assertEquals("Conversation proxy port is wrong.", 80, conv.getProxyPort());
    
    attrMock.verify();
    childAttrMock.verify();
  }

  public void testEmbeddedSuccess_ProxyHostNoPort() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.PROXY_HOST_ATTRIBUTE, "proxy.net");
    
    Mock attrMock = attributesFromMap(attributes);
    
    Mock childAttrMock = attributesEmpty();
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    TestConversationSubTag child = new TestConversationSubTag();
    
    child.setParent(tag);
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    tag.child(child);
    
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable("testresult", null);
    assertNotNull("conversation should be bound to context.", conv);
    assertEquals("Conversation proxy host is wrong.", "proxy.net", conv.getProxyHost());
    assertEquals("Conversation proxy port is wrong.", 80, conv.getProxyPort());
    
    attrMock.verify();
    childAttrMock.verify();
  }

  public void testEmbeddedSuccess_ProxyNoHostPort() throws TagException, TagalogParseException, MarmaladeExecutionException {
    System.out.println("testEmbeddedSuccess_ProxyNoHostPort");
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.PROXY_PORT_ATTRIBUTE, "80");
    
    Mock attrMock = attributesFromMap(attributes);
    
    Mock childAttrMock = attributesEmpty();
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    TestConversationSubTag child = new TestConversationSubTag();
    
    child.setParent(tag);
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    tag.child(child);
    
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable("testresult", null);
    assertNotNull("conversation should be bound to context.", conv);

    System.out.println("Proxy host: " + conv.getProxyHost());
    assertNull("Conversation proxy host should be null.", conv.getProxyHost());
//    assertEquals("Conversation proxy port is wrong.", 80, conv.getProxyPort());
    
    attrMock.verify();
    childAttrMock.verify();
  }
*/

  public void testSerialSuccess_NoAttributes() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String var = "conversation";
    Mock attrMock = attributesWithSingleAttribute(WebConversationTag.VAR_ATTRIBUTE, var);
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertNotNull("conversation should be bound to context.", (WebConversation)ctx.getVariable(var, null));
    
    attrMock.verify();
  }

  public void testSerialSuccess_UserPassword() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String var = "conversation";
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.VAR_ATTRIBUTE, var);
    attributes.put(WebConversationTag.USER_ATTRIBUTE, "testSerialusr");
    attributes.put(WebConversationTag.PASSWORD_ATTRIBUTE, "testSerialpass");
    
    Mock attrMock = attributesFromMap(attributes);
    
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable(var, null);
    assertNotNull("conversation should be bound to context.", conv);
    
    attrMock.verify();
  }

  public void testSerialSuccess_UserNoPassword() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String var = "conversation";
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.VAR_ATTRIBUTE, var);
    attributes.put(WebConversationTag.USER_ATTRIBUTE, "testSerialusr");
    
    Mock attrMock = attributesFromMap(attributes);
    
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable(var, null);
    assertNotNull("conversation should be bound to context.", conv);
    
    attrMock.verify();
  }

  public void testSerialSuccess_NoUserPassword() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String var = "conversation";
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.VAR_ATTRIBUTE, var);
    attributes.put(WebConversationTag.PASSWORD_ATTRIBUTE, "testSerialpass");
    
    Mock attrMock = attributesFromMap(attributes);
    
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable(var, null);
    assertNotNull("conversation should be bound to context.", conv);
    
    attrMock.verify();
  }
  
  public void testSerialSuccess_FailOnError() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String var = "conversation";
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.VAR_ATTRIBUTE, var);
    attributes.put(WebConversationTag.FAIL_ON_ERROR_ATTRIBUTE, "true");
    
    Mock attrMock = attributesFromMap(attributes);
    
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable(var, null);
    assertNotNull("conversation should be bound to context.", conv);
    assertTrue("Conversation should be set to fail on error", conv.getExceptionsThrownOnErrorStatus());
    
    attrMock.verify();
  }
  
  public void testSerialFail_BadFailOnError() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String var = "conversation";
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.VAR_ATTRIBUTE, var);
    attributes.put(WebConversationTag.FAIL_ON_ERROR_ATTRIBUTE, "invalid");
    
    Mock attrMock = attributesFromMap(attributes);
    
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("Should have throw an expression error for the bad failOnError attribute value");
    }
    catch(ExpressionEvaluationException e) {}
    
    attrMock.verify();
  }
  
  public void testSerialSuccess_DialogResponder() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String var = "conversation";
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.VAR_ATTRIBUTE, var);
    attributes.put(WebConversationTag.DIALOG_RESPONDER_ATTRIBUTE, "#reponder");
    
    Mock attrMock = attributesFromMap(attributes);
    
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    tag.end("conversation");
    
    Mock dlgRespMock = new Mock(DialogResponder.class);
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("responder", (DialogResponder)dlgRespMock.proxy());
    
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable(var, null);
    assertNotNull("conversation should be bound to context.", conv);
    
    System.out.println(
      "Warning!!! Cannot properly verify that the dialog responder was set. " +
      "API does not provide the means."
    );
    
    attrMock.verify();
    dlgRespMock.verify();
  }
  
/* 4/29/2004: Commenting out, because HTTPUnit's proxy settings are currently global to the VM.
 * Therefore, we should supply another tag to create a WebClient and use that to config a global
 * proxy setting if that's what we need to do. This will make the global nature of the proxy setting
 * more intuitive.
  public void testSerialSuccess_ProxyHostPort() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String var = "conversation";
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.VAR_ATTRIBUTE, var);
    attributes.put(WebConversationTag.PROXY_HOST_ATTRIBUTE, "proxy.net");
    attributes.put(WebConversationTag.PROXY_PORT_ATTRIBUTE, "80");
    
    Mock attrMock = attributesFromMap(attributes);
    
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable(var, null);
    assertNotNull("conversation should be bound to context.", conv);
    assertEquals("Conversation proxy host is wrong.", "proxy.net", conv.getProxyHost());
    assertEquals("Conversation proxy port is wrong.", 80, conv.getProxyPort());
    
    attrMock.verify();
  }

  public void testSerialSuccess_ProxyHostNoPort() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String var = "conversation";
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.VAR_ATTRIBUTE, var);
    attributes.put(WebConversationTag.PROXY_HOST_ATTRIBUTE, "proxy.net");
    
    Mock attrMock = attributesFromMap(attributes);
    
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable(var, null);
    assertNotNull("conversation should be bound to context.", conv);
    assertEquals("Conversation proxy host is wrong.", "proxy.net", conv.getProxyHost());
    assertEquals("Conversation proxy port is wrong.", 80, conv.getProxyPort());
    
    attrMock.verify();
  }

  public void testSerialSuccess_ProxyNoHostPort() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String var = "conversation";
    Map attributes = new TreeMap();
    attributes.put(WebConversationTag.VAR_ATTRIBUTE, var);
    attributes.put(WebConversationTag.PROXY_PORT_ATTRIBUTE, "80");
    
    Mock attrMock = attributesFromMap(attributes);
    
    WebConversationTag tag = new WebConversationTag();
    tag.begin("conversation", (Attributes)attrMock.proxy());
    tag.end("conversation");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    WebConversation conv = (WebConversation)ctx.getVariable(var, null);
    assertNotNull("conversation should be bound to context.", conv);

    System.out.println("Proxy host: " + conv.getProxyHost());
    assertNull("Conversation proxy host should be null.", conv.getProxyHost());
//    assertEquals("Conversation proxy port is wrong.", 80, conv.getProxyPort());
    
    attrMock.verify();
  }
*/
}
