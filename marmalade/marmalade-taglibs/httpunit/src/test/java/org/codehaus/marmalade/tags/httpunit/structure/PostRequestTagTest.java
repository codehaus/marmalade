/* Created on Apr 26, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;
import org.jmock.MockObjectTestCase;
import org.jmock.cglib.Mock;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class PostRequestTagTest extends MockObjectTestCase{
    
    public void testShouldFailBecauseUrlAttributeNotSpecified() throws MarmaladeExecutionException {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        
        MarmaladeTagInfo ti = new MarmaladeTagInfo();
        ti.setAttributes(attributes);
        
        PostRequestTag tag = new PostRequestTag(ti);
        
        MarmaladeTagInfo childTI = new MarmaladeTagInfo();
        
        TestRequestSubTag child = new TestRequestSubTag(childTI);
        
        child.setParent(tag);
        tag.addChild(child);
        
        try {
            tag.execute(new DefaultContext());
            fail("Should fail because of missing url attribute");
        }
        catch(MissingAttributeException e) {
            // should fail.
            e.printStackTrace();
        }
    }
    
    public void testShouldSucceedWithNothingButUrlAttributeSpecified() throws MarmaladeExecutionException {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", PostRequestTag.URL_ATTRIBUTE, "http://localhost");
        
        MarmaladeTagInfo ti = new MarmaladeTagInfo();
        ti.setAttributes(attributes);
        
        PostRequestTag tag = new PostRequestTag(ti);
        
        MarmaladeTagInfo childTI = new MarmaladeTagInfo();
        
        TestRequestSubTag child = new TestRequestSubTag(childTI);
        
        child.setParent(tag);
        tag.addChild(child);
        
        tag.execute(new DefaultContext());
    }
    
/*    
  public void testEmbeddedSuccess() throws MarmaladeExecutionException {
    Mock convTagMock = new Mock(WebConversationTag.class);
    
    DefaultRawAttributes attributes = new DefaultRawAttributes();
    attributes.addAttribute(new DefaultRawAttribute("", PostRequestTag.URL_ATTRIBUTE, "http://localhost"));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attributes);
    mti.setElement("post");
    mti.setParent(null);
    
    PostRequestTag tag = new PostRequestTag(mti);
    
    MarmaladeTagInfo mtiChild = new MarmaladeTagInfo();
    mti.setElement("test");
    
    TestRequestSubTag child = new TestRequestSubTag(mtiChild);
    
    tag.addChild(child);
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertTrue("Child should have found request.", child.foundRequest());
  }
  
  public void testEmbeddedMissingConversation() 
  throws TagException, TagalogParseException, MarmaladeExecutionException 
  {
    DefaultRawAttributes attributes = new DefaultRawAttributes();
    attributes.addAttribute(new DefaultRawAttribute("", PostRequestTag.URL_ATTRIBUTE, "http://localhost"));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attributes);
    mti.setElement("post");
    mti.setParent(null);
    
    PostRequestTag tag = new PostRequestTag(mti);
    
    MarmaladeTagInfo mtiChild = new MarmaladeTagInfo();
    mti.setElement("test");
    mti.setParent(new TestParentBuilder(tag));
    
    TestRequestSubTag child = new TestRequestSubTag(mtiChild);
    
    tag.addChild(child);
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertTrue("Child should have found request.", child.foundRequest());
  }
  
  public void testSerialSuccess() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock convMock = new Mock(WebConversation.class);
    
    DefaultRawAttributes attributes = new DefaultRawAttributes();
    attributes.addAttribute(new DefaultRawAttribute("", PostRequestTag.VAR_ATTRIBUTE, "request"));
    attributes.addAttribute(new DefaultRawAttribute("", PostRequestTag.URL_ATTRIBUTE, "http://localhost"));
    attributes.addAttribute(new DefaultRawAttribute("", PostRequestTag.CONVERSATION_ATTRIBUTE, "#conversation"));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attributes);
    mti.setElement("post");
    mti.setParent(null);
    
    PostRequestTag tag = new PostRequestTag(mti);
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("conversation", (WebConversation)convMock.proxy());
    tag.execute(ctx);
    
    assertNotNull("Request variable should NOT be null.", ctx.getVariable("request", null));
  }

  public void testSerialMissingConversation() throws TagException, TagalogParseException, MarmaladeExecutionException {
    DefaultRawAttributes attributes = new DefaultRawAttributes();
    attributes.addAttribute(new DefaultRawAttribute("", PostRequestTag.VAR_ATTRIBUTE, "request"));
    attributes.addAttribute(new DefaultRawAttribute("", PostRequestTag.URL_ATTRIBUTE, "http://localhost"));
    attributes.addAttribute(new DefaultRawAttribute("", PostRequestTag.CONVERSATION_ATTRIBUTE, "#conversation"));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attributes);
    mti.setElement("post");
    mti.setParent(null);
    
    PostRequestTag tag = new PostRequestTag(mti);
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertNotNull("Request variable should NOT be null.", ctx.getVariable("request", null));
  }

  public void testAllAttributes() 
  throws TagException, TagalogParseException, MarmaladeExecutionException 
  {
    DefaultRawAttributes attributes = new DefaultRawAttributes();
    attributes.addAttribute(new DefaultRawAttribute("", PostRequestTag.VAR_ATTRIBUTE, "request"));
    attributes.addAttribute(new DefaultRawAttribute("", PostRequestTag.BASE_URL_ATTRIBUTE, "http://localhost"));
    attributes.addAttribute(new DefaultRawAttribute("", PostRequestTag.URL_ATTRIBUTE, "/index.html"));
    attributes.addAttribute(new DefaultRawAttribute("", PostRequestTag.TARGET_ATTRIBUTE, "_new"));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attributes);
    mti.setElement("post");
    mti.setParent(null);
    
    PostRequestTag tag = new PostRequestTag(mti);
    
    MarmaladeTagInfo mtiChild = new MarmaladeTagInfo();
    mti.setElement("test");
    mti.setParent(new TestParentBuilder(tag));
    
    TestRequestSubTag child = new TestRequestSubTag(mtiChild);
    
    tag.addChild(child);
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);

    assertTrue("Tag child should have found request.", child.foundRequest());
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
    
    PostRequestTag tag = new PostRequestTag();
    tag.begin("post", (Attributes)reqAttrMock.proxy());
    
    Mock childAttrMock = attributesEmpty();
    TestRequestSubTag child = new TestRequestSubTag();
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    
    child.setParent(tag);
    tag.child(child);
    tag.end("post");
    
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
    
    PostRequestTag tag = new PostRequestTag();
    tag.begin("post", (Attributes)reqAttrMock.proxy());
    tag.end("post");
    
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
    
    PostRequestTag tag = new PostRequestTag();
    tag.begin("post", (Attributes)reqAttrMock.proxy());
    
    Mock childAttrMock = attributesEmpty();
    TestRequestSubTag child = new TestRequestSubTag();
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    
    child.setParent(tag);
    tag.child(child);
    tag.end("post");
    
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
    
    PostRequestTag tag = new PostRequestTag();
    tag.begin("post", (Attributes)reqAttrMock.proxy());
    
    Mock childAttrMock = attributesEmpty();
    TestRequestSubTag child = new TestRequestSubTag();
    child.begin("child", (Attributes)childAttrMock.proxy());
    child.end("child");
    
    child.setParent(tag);
    tag.child(child);
    tag.end("post");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);

    assertTrue("Tag child should have found request.", child.foundRequest());
    
    reqAttrMock.verify();
    childAttrMock.verify();
  }
*/
}
