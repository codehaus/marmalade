/* Created on Apr 25, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.defaults.DefaultContext;
import org.codehaus.marmalade.tags.httpunit.structure.WebResponseTag;
import org.codehaus.marmalade.testing.AbstractTagCGLibTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.cglib.Mock;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class HasElementTagTest extends AbstractTagCGLibTestCase{

  public void testSuccessWithName() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String name = "h1";
    
    Mock elementMock = new Mock(HTMLElement.class);
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getElementsWithName")
                .with(eq(name))
                .will(returnValue(new HTMLElement[] {(HTMLElement)elementMock.proxy()}));
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Mock attrMock = attributesWithSingleAttribute("withName", name);
    
    HasElementTag tag = new HasElementTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasElement", (Attributes)attrMock.proxy());
    tag.end("hasElement");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    elementMock.verify();
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
  }
  
  public void testFailWithName() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String name = "h1";
    
    Mock elementMock = new Mock(HTMLElement.class);
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getElementsWithName")
                .with(eq(name))
                .will(returnValue(new HTMLElement[0]));
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Mock attrMock = attributesWithSingleAttribute("withName", name);
    
    HasElementTag tag = new HasElementTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasElement", (Attributes)attrMock.proxy());
    tag.end("hasElement");
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("Titles should not match, and an exception should have been thrown.");
    }
    catch(HttpAssertionFailedException e) {
    }
    
    elementMock.verify();
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
  }
  
  public void testSuccessWithId() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String id = "1";
    
    Mock elementMock = new Mock(HTMLElement.class);
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getElementWithID")
                .with(eq(id))
                .will(returnValue((HTMLElement)elementMock.proxy()));
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Mock attrMock = attributesWithSingleAttribute("withId", id);
    
    HasElementTag tag = new HasElementTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasElement", (Attributes)attrMock.proxy());
    tag.end("hasElement");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    elementMock.verify();
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
  }
  
  public void testFailWithId() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String id = "1";
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getElementWithID")
                .with(eq(id))
                .will(returnValue(null));
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Mock attrMock = attributesWithSingleAttribute("withId", id);
    
    HasElementTag tag = new HasElementTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasElement", (Attributes)attrMock.proxy());
    tag.end("hasElement");
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("An exception should have been thrown.");
    }
    catch(HttpAssertionFailedException e) {
    }
    
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
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
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put("withName", name);
    attributes.put("withClassName", className);
    
    Mock attrMock = attributesFromMap(attributes);
    
    HasElementTag tag = new HasElementTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasElement", (Attributes)attrMock.proxy());
    tag.end("hasElement");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    elementMock.verify();
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
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
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put("withName", name);
    attributes.put("withClassName", "className");
    
    Mock attrMock = attributesFromMap(attributes);
    
    HasElementTag tag = new HasElementTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasElement", (Attributes)attrMock.proxy());
    tag.end("hasElement");
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("An exception should have been thrown.");
    }
    catch(HttpAssertionFailedException e) {
    }
    
    elementMock.verify();
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
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
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put("withName", name);
    attributes.put("withTitle", title);
    
    Mock attrMock = attributesFromMap(attributes);
    
    HasElementTag tag = new HasElementTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasElement", (Attributes)attrMock.proxy());
    tag.end("hasElement");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    elementMock.verify();
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
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
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put("withName", name);
    attributes.put("withTitle", title);
    
    Mock attrMock = attributesFromMap(attributes);
    
    HasElementTag tag = new HasElementTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasElement", (Attributes)attrMock.proxy());
    tag.end("hasElement");
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("An exception should have been thrown.");
    }
    catch(HttpAssertionFailedException e) {
    }
    
    elementMock.verify();
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
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
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put("withName", name);
    attributes.put("withId", id);
    
    Mock attrMock = attributesFromMap(attributes);
    
    HasElementTag tag = new HasElementTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasElement", (Attributes)attrMock.proxy());
    tag.end("hasElement");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    elementMock.verify();
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
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
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put("withName", name);
    attributes.put("withId", id);
    
    Mock attrMock = attributesFromMap(attributes);
    
    HasElementTag tag = new HasElementTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasElement", (Attributes)attrMock.proxy());
    tag.end("hasElement");
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("An exception should have been thrown.");
    }
    catch(HttpAssertionFailedException e) {
    }
    
    elementMock.verify();
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
  }
  
}
