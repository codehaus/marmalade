/* Created on Apr 26, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.jsp.el.ExpressionEvaluator;

import org.codehaus.marmalade.IllegalAncestorException;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.MissingAttributeException;
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
public class GetRequestTagTest extends AbstractTagCGLibTestCase{
  
  public void testEmbeddedSuccess() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock convTagMock = new Mock(DefaultWebConversationTag.class);
    
    Map attributes = new TreeMap();
    attributes.put("url", "http://localhost");
    
    Mock reqAttrMock = attributesFromMap(attributes);
    GetRequestTag tag = new GetRequestTag();
    tag.setParent((Tag)convTagMock.proxy());
    
    tag.begin("get", (Attributes)reqAttrMock.proxy());
    
    Mock childAttrMock = attributesEmpty();
    TestRequestSubTag child = new TestRequestSubTag();
    child.setParent(tag);
    child.begin("test", (Attributes)childAttrMock.proxy());
    child.end("test");
    
    tag.child(child);
    tag.end("get");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertTrue("Child should have found request.", child.foundRequest());
    
    convTagMock.verify();
    reqAttrMock.verify();
    childAttrMock.verify();
  }
  
  public void testEmbeddedMissingConversation() 
  throws TagException, TagalogParseException, MarmaladeExecutionException 
  {
    Map attributes = new TreeMap();
    attributes.put("url", "http://localhost");
    
    Mock reqAttrMock = attributesFromMap(attributes);
    GetRequestTag tag = new GetRequestTag();
    
    tag.begin("get", (Attributes)reqAttrMock.proxy());
    
    Mock childAttrMock = attributesEmpty();
    TestRequestSubTag child = new TestRequestSubTag();
    child.setParent(tag);
    child.begin("test", (Attributes)childAttrMock.proxy());
    child.end("test");
    
    tag.child(child);
    tag.end("get");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertTrue("Child should have found request.", child.foundRequest());
    
    reqAttrMock.verify();
    childAttrMock.verify();
  }
  
  public void testSerialSuccess() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock convMock = new Mock(WebConversation.class);
    
    Map attributes = new TreeMap();
    attributes.put("var", "request");
    attributes.put("url", "http://localhost");
    attributes.put("conversation", "#conversation");
    
    Mock reqAttrMock = attributesFromMap(attributes);
    
    GetRequestTag tag = new GetRequestTag();
    tag.begin("get", (Attributes)reqAttrMock.proxy());
    tag.end("get");
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("conversation", (WebConversation)convMock.proxy());
    tag.execute(ctx);
    
    assertNotNull("Request variable should NOT be null.", ctx.getVariable("request", null));
    
    convMock.verify();
    reqAttrMock.verify();
  }

  public void testSerialMissingConversation() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Map attributes = new TreeMap();
    attributes.put("var", "request");
    attributes.put("url", "http://localhost");
    attributes.put("conversation", "#conversation");
    
    Mock reqAttrMock = attributesFromMap(attributes);
    
    GetRequestTag tag = new GetRequestTag();
    tag.begin("get", (Attributes)reqAttrMock.proxy());
    tag.end("get");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertNotNull("Request variable should NOT be null.", ctx.getVariable("request", null));
    
    reqAttrMock.verify();
  }

  public void testAllAttributes() 
  throws TagException, TagalogParseException, MarmaladeExecutionException 
  {
    Map attributes = new TreeMap();
    attributes.put("var", "request");
    attributes.put("baseUrl", "http://localhost");
    attributes.put("url", "/index.html");
    attributes.put("target", "_new");
    
    Mock reqAttrMock = attributesFromMap(attributes);
    
    GetRequestTag tag = new GetRequestTag();
    tag.begin("get", (Attributes)reqAttrMock.proxy());
    
    Mock childAttrMock = attributesEmpty();
    TestRequestSubTag child = new TestRequestSubTag();
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    
    child.setParent(tag);
    tag.child(child);
    tag.end("get");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);

    assertTrue("Tag child should have found request.", child.foundRequest());
    
    reqAttrMock.verify();
    childAttrMock.verify();
  }

  public void testAllAttributes_BaseUrlIsObject() 
  throws TagException, TagalogParseException, MarmaladeExecutionException, MalformedURLException 
  {
    Map attributes = new TreeMap();
    attributes.put("var", "request");
    attributes.put("baseUrl", "#baseUrl");
    attributes.put("url", "/index.html");
    attributes.put("target", "_new");
    
    Mock reqAttrMock = attributesFromMap(attributes);
    
    GetRequestTag tag = new GetRequestTag();
    tag.begin("get", (Attributes)reqAttrMock.proxy());
    
    Mock childAttrMock = attributesEmpty();
    TestRequestSubTag child = new TestRequestSubTag();
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    
    child.setParent(tag);
    tag.child(child);
    tag.end("get");
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("baseUrl", new URL("http://localhost"));
    tag.execute(ctx);

    assertTrue("Tag child should have found request.", child.foundRequest());
    
    reqAttrMock.verify();
    childAttrMock.verify();
  }

  public void testMissingUrl() 
  throws TagException, TagalogParseException, MarmaladeExecutionException 
  {
    Map attributes = new TreeMap();
    attributes.put("var", "request");
    attributes.put("baseUrl", "http://localhost");
    attributes.put("target", "_new");
    
    Mock reqAttrMock = attributesFromMap(attributes);
    
    GetRequestTag tag = new GetRequestTag();
    tag.begin("get", (Attributes)reqAttrMock.proxy());
    tag.end("get");
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("Should throw exception for missing url attribute");
    }
    catch(MissingAttributeException e ) {}
    
    reqAttrMock.verify();
  }

  public void testMissingBaseUrl() 
  throws TagException, TagalogParseException, MarmaladeExecutionException 
  {
    Map attributes = new TreeMap();
    attributes.put("var", "request");
    attributes.put("url", "/index.html");
    attributes.put("target", "_new");
    
    Mock reqAttrMock = attributesFromMap(attributes);
    
    GetRequestTag tag = new GetRequestTag();
    tag.begin("get", (Attributes)reqAttrMock.proxy());
    
    Mock childAttrMock = attributesEmpty();
    TestRequestSubTag child = new TestRequestSubTag();
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    
    child.setParent(tag);
    tag.child(child);
    tag.end("get");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);

    assertTrue("Tag child should have found request.", child.foundRequest());
    
    reqAttrMock.verify();
    childAttrMock.verify();
  }

  public void testMissingTarget() 
  throws TagException, TagalogParseException, MarmaladeExecutionException 
  {
    Map attributes = new TreeMap();
    attributes.put("var", "request");
    attributes.put("baseUrl", "http://localhost");
    attributes.put("url", "/index.html");
    
    Mock reqAttrMock = attributesFromMap(attributes);
    
    GetRequestTag tag = new GetRequestTag();
    tag.begin("get", (Attributes)reqAttrMock.proxy());
    
    Mock childAttrMock = attributesEmpty();
    TestRequestSubTag child = new TestRequestSubTag();
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    
    child.setParent(tag);
    tag.child(child);
    tag.end("get");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);

    assertTrue("Tag child should have found request.", child.foundRequest());
    
    reqAttrMock.verify();
    childAttrMock.verify();
  }

}
