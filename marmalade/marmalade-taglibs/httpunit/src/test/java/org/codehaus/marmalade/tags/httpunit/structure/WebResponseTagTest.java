/* Created on Apr 26, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.jsp.el.ExpressionEvaluator;

import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.IllegalAncestorException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.TestingWebRequest;
import org.codehaus.marmalade.tags.httpunit.TestingWebResponse;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.MockObjectTestCase;
import org.jmock.cglib.Mock;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class WebResponseTagTest extends MockObjectTestCase{
  
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
    
    Mock reqTagMock = new Mock(WebRequestTag.class);
    reqTagMock.expects(once())
              .method("getRequest")
              .withNoArguments()
              .will(returnValue(reqMock.proxy()));
    reqTagMock.expects(once())
              .method("getParent")
              .withNoArguments()
              .will(returnValue(convTagMock.proxy()));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(new DefaultRawAttributes());
    mti.setElement("response");
    
    DefaultWebResponseTag tag = new DefaultWebResponseTag(mti);
    tag.setParent((MarmaladeTag)reqTagMock.proxy());
    
    MarmaladeTagInfo mtiChild = new MarmaladeTagInfo();
    mtiChild.setAttributes(new DefaultRawAttributes());
    mtiChild.setElement("child");
    mtiChild.setParent(null);
    
    TestResponseSubTag child = new TestResponseSubTag(mtiChild);
    
    tag.addChild(child);
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertTrue("Child should have found response.", child.foundResponse());
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
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(new DefaultRawAttributes());
    mti.setElement("response");
    
    DefaultWebResponseTag tag = new DefaultWebResponseTag(mti);
    tag.setParent((MarmaladeTag)convTagMock.proxy());
    
    MarmaladeTagInfo mtiChild = new MarmaladeTagInfo();
    mtiChild.setAttributes(new DefaultRawAttributes());
    mtiChild.setElement("child");
    
    TestResponseSubTag child = new TestResponseSubTag(mtiChild);
    
    child.setParent(tag);
    tag.addChild(child);
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("Should fail with missing request ancestor.");
    }
    catch(IllegalAncestorException e) {}
    
    assertFalse("Child should NOT have found response.", child.foundResponse());
  }
  
  public void testEmbeddedMissingConversation() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock reqTagMock = new Mock(AbstractWebRequestTag.class);
    reqTagMock.expects(once())
              .method("getParent")
              .withNoArguments()
              .will(returnValue(null));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(new DefaultRawAttributes());
    mti.setElement("response");
    
    DefaultWebResponseTag tag = new DefaultWebResponseTag(mti);
    tag.setParent((MarmaladeTag)reqTagMock.proxy());
    
    MarmaladeTagInfo mtiChild = new MarmaladeTagInfo();
    mtiChild.setAttributes(new DefaultRawAttributes());
    mtiChild.setElement("child");
    mtiChild.setParent(null);
    
    TestResponseSubTag child = new TestResponseSubTag(mtiChild);
    
    tag.addChild(child);
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("Exec should fail with missing conversation ancestor");
    }
    catch(IllegalAncestorException e) {}
    
    assertFalse("Child should NOT have found response.", child.foundResponse());
  }
  
  public void testSerialSuccess() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock respMock = new Mock(TestingWebResponse.class);
    Mock convMock = new Mock(WebConversation.class);
    convMock.expects(once())
            .method("getResponse")
            .with(isA(WebRequest.class))
            .will(returnValue(respMock.proxy()));
    
    Mock reqMock = new Mock(TestingWebRequest.class);
    
    DefaultRawAttributes attributes = new DefaultRawAttributes();
    attributes.addAttribute(new DefaultRawAttribute("", "var", "response"));
    attributes.addAttribute(new DefaultRawAttribute("", "conversation", "#conversation"));
    attributes.addAttribute(new DefaultRawAttribute("", "request", "#request"));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attributes);
    mti.setElement("response");
    
    DefaultWebResponseTag tag = new DefaultWebResponseTag(mti);
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("conversation", (WebConversation)convMock.proxy());
    ctx.setVariable("request", (WebRequest)reqMock.proxy());
    tag.execute(ctx);
    
    assertNotNull("Response variable should NOT be null.", ctx.getVariable("response", null));
  }

  public void testSerialMissingConversation() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock reqMock = new Mock(TestingWebRequest.class);
    
    DefaultRawAttributes attributes = new DefaultRawAttributes();
    attributes.addAttribute(new DefaultRawAttribute("", "var", "response"));
    attributes.addAttribute(new DefaultRawAttribute("", "conversation", "#conversation"));
    attributes.addAttribute(new DefaultRawAttribute("", "request", "#request"));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attributes);
    mti.setElement("response");
    
    DefaultWebResponseTag tag = new DefaultWebResponseTag(mti);
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("request", (WebRequest)reqMock.proxy());
    try {
      tag.execute(ctx);
      fail("Should throw exception for missing conversation.");
    }
    catch(IllegalAncestorException e) {}
    
    assertNull("Response variable should be null.", ctx.getVariable("response", null));
  }

  public void testSerialMissingRequest() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock convMock = new Mock(WebConversation.class);
    
    DefaultRawAttributes attributes = new DefaultRawAttributes();
    attributes.addAttribute(new DefaultRawAttribute("", "var", "response"));
    attributes.addAttribute(new DefaultRawAttribute("", "conversation", "#conversation"));
    attributes.addAttribute(new DefaultRawAttribute("", "request", "#request"));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attributes);
    mti.setElement("response");
    
    DefaultWebResponseTag tag = new DefaultWebResponseTag(mti);
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("conversation", (WebConversation)convMock.proxy());
    try {
      tag.execute(ctx);
      fail("Should throw exception for missing request.");
    }
    catch(IllegalAncestorException e) {}
    
    assertNull("Response variable should be null.", ctx.getVariable("response", null));
  }

}
