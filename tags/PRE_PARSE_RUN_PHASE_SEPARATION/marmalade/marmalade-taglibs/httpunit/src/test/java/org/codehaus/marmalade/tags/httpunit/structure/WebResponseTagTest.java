/* Created on Apr 26, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.jsp.el.ExpressionEvaluator;

import org.codehaus.marmalade.IllegalAncestorException;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.defaults.DefaultContext;
import org.codehaus.marmalade.tags.httpunit.TestingWebRequest;
import org.codehaus.marmalade.tags.httpunit.TestingWebResponse;
import org.codehaus.marmalade.testing.AbstractTagCGLibTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.cglib.Mock;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class WebResponseTagTest extends AbstractTagCGLibTestCase{
  
  public void testEmbeddedSuccess() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock respMock = new Mock(TestingWebResponse.class);
    Mock convMock = new Mock(WebConversation.class);
    convMock.expects(once())
            .method("getResponse")
            .with(isA(WebRequest.class))
            .will(returnValue(respMock.proxy()));
    
    Mock convTagMock = new Mock(WebConversationTag.class);
    convTagMock.expects(once())
               .method("getConversation")
               .withNoArguments()
               .will(returnValue(convMock.proxy()));
    
    Mock reqMock = new Mock(TestingWebRequest.class);
    
    Mock reqTagMock = new Mock(AbstractWebRequestTag.class);
    reqTagMock.expects(once())
              .method("getRequest")
              .withNoArguments()
              .will(returnValue(reqMock.proxy()));
    reqTagMock.expects(once())
              .method("getParent")
              .withNoArguments()
              .will(returnValue(convTagMock.proxy()));
    
    Mock respAttrMock = attributesEmpty();
    WebResponseTag tag = new WebResponseTag();
    tag.setParent((Tag)reqTagMock.proxy());
    
    tag.begin("response", (Attributes)respAttrMock.proxy());
    
    Mock childAttrMock = attributesEmpty();
    TestResponseSubTag child = new TestResponseSubTag();
    child.setParent(tag);
    child.begin("test", (Attributes)childAttrMock.proxy());
    child.end("test");
    
    tag.child(child);
    tag.end("response");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertTrue("Child should have found response.", child.foundResponse());
    
    respMock.verify();
    convMock.verify();
    convTagMock.verify();
    reqMock.verify();
    reqTagMock.verify();
    respAttrMock.verify();
    childAttrMock.verify();
  }
  
  public void testEmbeddedMissingRequest() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock convMock = new Mock(WebConversation.class);
    
    Mock convTagMock = new Mock(WebConversationTag.class);
    convTagMock.expects(once())
               .method("getConversation")
               .withNoArguments()
               .will(returnValue(convMock.proxy()));
    convTagMock.expects(once())
               .method("getParent")
               .withNoArguments()
               .will(returnValue(null));
    
    Mock respAttrMock = attributesEmpty();
    WebResponseTag tag = new WebResponseTag();
    tag.setParent((Tag)convTagMock.proxy());
    
    tag.begin("response", (Attributes)respAttrMock.proxy());
    
    Mock childAttrMock = attributesEmpty();
    TestResponseSubTag child = new TestResponseSubTag();
    child.setParent(tag);
    child.begin("test", (Attributes)childAttrMock.proxy());
    child.end("test");
    
    tag.child(child);
    tag.end("response");
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("Should fail with missing request ancestor.");
    }
    catch(IllegalAncestorException e) {}
    
    assertFalse("Child should NOT have found response.", child.foundResponse());
    
    convMock.verify();
    convTagMock.verify();
    respAttrMock.verify();
    childAttrMock.verify();
  }
  
  public void testEmbeddedMissingConversation() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock reqTagMock = new Mock(AbstractWebRequestTag.class);
    reqTagMock.expects(once())
              .method("getParent")
              .withNoArguments()
              .will(returnValue(null));
    
    Mock respAttrMock = attributesEmpty();
    WebResponseTag tag = new WebResponseTag();
    tag.setParent((Tag)reqTagMock.proxy());
    
    tag.begin("response", (Attributes)respAttrMock.proxy());
    
    Mock childAttrMock = attributesEmpty();
    TestResponseSubTag child = new TestResponseSubTag();
    child.setParent(tag);
    child.begin("test", (Attributes)childAttrMock.proxy());
    child.end("test");
    
    tag.child(child);
    tag.end("response");
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("Exec should fail with missing conversation ancestor");
    }
    catch(IllegalAncestorException e) {}
    
    assertFalse("Child should NOT have found response.", child.foundResponse());
    
    respAttrMock.verify();
    childAttrMock.verify();
  }
  
  public void testSerialSuccess() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock respMock = new Mock(TestingWebResponse.class);
    Mock convMock = new Mock(WebConversation.class);
    convMock.expects(once())
            .method("getResponse")
            .with(isA(WebRequest.class))
            .will(returnValue(respMock.proxy()));
    
    Mock reqMock = new Mock(TestingWebRequest.class);
    
    Map attributes = new TreeMap();
    attributes.put("var", "response");
    attributes.put("conversation", "#conversation");
    attributes.put("request", "#request");
    
    Mock respAttrMock = attributesFromMap(attributes);
    
    WebResponseTag tag = new WebResponseTag();
    tag.begin("response", (Attributes)respAttrMock.proxy());
    tag.end("response");
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("conversation", (WebConversation)convMock.proxy());
    ctx.setVariable("request", (WebRequest)reqMock.proxy());
    tag.execute(ctx);
    
    assertNotNull("Response variable should NOT be null.", ctx.getVariable("response", null));
    
    respMock.verify();
    convMock.verify();
    reqMock.verify();
    respAttrMock.verify();
  }

  public void testSerialMissingConversation() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock reqMock = new Mock(TestingWebRequest.class);
    
    Map attributes = new TreeMap();
    attributes.put("var", "response");
    attributes.put("conversation", "#conversation");
    attributes.put("request", "#request");
    
    Mock respAttrMock = attributesFromMap(attributes);
    
    WebResponseTag tag = new WebResponseTag();
    tag.begin("response", (Attributes)respAttrMock.proxy());
    tag.end("response");
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("request", (WebRequest)reqMock.proxy());
    try {
      tag.execute(ctx);
      fail("Should throw exception for missing conversation.");
    }
    catch(IllegalAncestorException e) {}
    
    assertNull("Response variable should be null.", ctx.getVariable("response", null));
    
    reqMock.verify();
    respAttrMock.verify();
  }

  public void testSerialMissingRequest() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock convMock = new Mock(WebConversation.class);
    
    Map attributes = new TreeMap();
    attributes.put("var", "response");
    attributes.put("conversation", "#conversation");
    attributes.put("request", "#request");
    
    Mock respAttrMock = attributesFromMap(attributes);
    
    WebResponseTag tag = new WebResponseTag();
    tag.begin("response", (Attributes)respAttrMock.proxy());
    tag.end("response");
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("conversation", (WebConversation)convMock.proxy());
    try {
      tag.execute(ctx);
      fail("Should throw exception for missing request.");
    }
    catch(IllegalAncestorException e) {}
    
    assertNull("Response variable should be null.", ctx.getVariable("response", null));
    
    convMock.verify();
    respAttrMock.verify();
  }

}
