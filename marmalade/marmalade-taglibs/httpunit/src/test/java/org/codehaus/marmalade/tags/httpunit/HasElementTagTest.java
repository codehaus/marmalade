/* Created on Apr 25, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.structure.DefaultWebResponseTag;
import org.codehaus.marmalade.tags.httpunit.structure.WebResponseTag;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.MockObjectTestCase;
import org.jmock.cglib.Mock;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class HasElementTagTest extends MockObjectTestCase{

  public void testSuccessWithName() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String name = "h1";
    
    Mock elementMock = new Mock(HTMLElement.class);
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getElementsWithName")
                .with(eq(name))
                .will(returnValue(new HTMLElement[] {(HTMLElement)elementMock.proxy()}));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "withName", name));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attrs);
    mti.setElement("hasElement");
    
    HasElementTag tag = new HasElementTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
  }
  
  public void testFailWithName() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String name = "h1";
    
    Mock elementMock = new Mock(HTMLElement.class);
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getElementsWithName")
                .with(eq(name))
                .will(returnValue(new HTMLElement[0]));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "withName", name));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attrs);
    mti.setElement("hasElement");
    
    HasElementTag tag = new HasElementTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("Titles should not match, and an exception should have been thrown.");
    }
    catch(HttpAssertionFailedException e) {
    }
  }
  
  public void testSuccessWithId() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String id = "1";
    
    Mock elementMock = new Mock(HTMLElement.class);
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getElementWithID")
                .with(eq(id))
                .will(returnValue((HTMLElement)elementMock.proxy()));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "withId", id));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attrs);
    mti.setElement("hasElement");
    
    HasElementTag tag = new HasElementTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
  }
  
  public void testFailWithId() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String id = "1";
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getElementWithID")
                .with(eq(id))
                .will(returnValue(null));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "withId", id));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attrs);
    mti.setElement("hasElement");
    
    HasElementTag tag = new HasElementTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("An exception should have been thrown.");
    }
    catch(HttpAssertionFailedException e) {
    }
  }
  
  public void testSuccessWithName_ClassName() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String name = "h1";
    String className = "testClass";
    
    Mock elementMock = new Mock(HTMLElement.class);
    elementMock.expects(once())
               .method("getClassName")
               .withNoArguments()
               .will(returnValue(className));
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getElementsWithName")
                .with(eq(name))
                .will(returnValue(new HTMLElement[] {(HTMLElement)elementMock.proxy()}));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "withName", name));
    attrs.addAttribute(new DefaultRawAttribute("", "withClassName", className));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attrs);
    mti.setElement("hasElement");
    
    HasElementTag tag = new HasElementTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
  }
  
  public void testFailWithName_ClassName() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String name = "h1";
    String className = "testClass";
    
    Mock elementMock = new Mock(HTMLElement.class);
    elementMock.expects(once())
               .method("getClassName")
               .withNoArguments()
               .will(returnValue("testClassNOMATCH"));
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getElementsWithName")
                .with(eq(name))
                .will(returnValue(new HTMLElement[] {(HTMLElement)elementMock.proxy()}));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "withName", name));
    attrs.addAttribute(new DefaultRawAttribute("", "withClassName", className));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attrs);
    mti.setElement("hasElement");
    
    HasElementTag tag = new HasElementTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("An exception should have been thrown.");
    }
    catch(HttpAssertionFailedException e) {
    }
  }
  
  public void testSuccessWithName_Title() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String name = "h1";
    String title = "testTitle";
    
    Mock elementMock = new Mock(HTMLElement.class);
    elementMock.expects(once())
               .method("getTitle")
               .withNoArguments()
               .will(returnValue(title));
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getElementsWithName")
                .with(eq(name))
                .will(returnValue(new HTMLElement[] {(HTMLElement)elementMock.proxy()}));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "withName", name));
    attrs.addAttribute(new DefaultRawAttribute("", "withTitle", title));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attrs);
    mti.setElement("hasElement");
    
    HasElementTag tag = new HasElementTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
  }
  
  public void testFailWithName_Title() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String name = "h1";
    String title = "testTitle";
    
    Mock elementMock = new Mock(HTMLElement.class);
    elementMock.expects(once())
               .method("getTitle")
               .withNoArguments()
               .will(returnValue("testTitleNOMATCH"));
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getElementsWithName")
                .with(eq(name))
                .will(returnValue(new HTMLElement[] {(HTMLElement)elementMock.proxy()}));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "withName", name));
    attrs.addAttribute(new DefaultRawAttribute("", "withTitle", title));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attrs);
    mti.setElement("hasElement");
    
    HasElementTag tag = new HasElementTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("An exception should have been thrown.");
    }
    catch(HttpAssertionFailedException e) {
    }
  }
  
  public void testSuccessWithId_Name() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String name = "h1";
    String id = "1";
    
    Mock elementMock = new Mock(HTMLElement.class);
    elementMock.expects(once())
               .method("getName")
               .withNoArguments()
               .will(returnValue(name));
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getElementWithID")
                .with(eq(id))
                .will(returnValue((HTMLElement)elementMock.proxy()));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "withName", name));
    attrs.addAttribute(new DefaultRawAttribute("", "withId", id));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attrs);
    mti.setElement("hasElement");
    
    HasElementTag tag = new HasElementTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
  }
  
  public void testFailWithId_Name() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String name = "h1";
    String id = "1";
    
    Mock elementMock = new Mock(HTMLElement.class);
    elementMock.expects(once())
               .method("getName")
               .withNoArguments()
               .will(returnValue("h2"));
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getElementWithID")
                .with(eq(id))
                .will(returnValue((HTMLElement)elementMock.proxy()));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "withName", name));
    attrs.addAttribute(new DefaultRawAttribute("", "withId", id));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attrs);
    mti.setElement("hasElement");
    
    HasElementTag tag = new HasElementTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("An exception should have been thrown.");
    }
    catch(HttpAssertionFailedException e) {
    }
  }
  
}
