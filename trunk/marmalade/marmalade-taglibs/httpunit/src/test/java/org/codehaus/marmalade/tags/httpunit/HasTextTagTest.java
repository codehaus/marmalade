/* Created on Apr 25, 2004 */
package org.codehaus.marmalade.tags.httpunit;

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

import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class HasTextTagTest extends MockObjectTestCase{

  public void testSucceedFullMatch() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String text = "<h1>This is a test</h1>";
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getText")
                .withNoArguments()
                .will(returnValue(text));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "withValue", text));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attrs);
    mti.setElement("hasText");
    
    HasTextTag tag = new HasTextTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
  }
  
  public void testSucceedSubstring() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String text = "<h1>This is a test</h1>";
    String text2 = "This is";
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getText")
                .withNoArguments()
                .will(returnValue(text));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "withValue", text2));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attrs);
    mti.setElement("hasText");
    
    HasTextTag tag = new HasTextTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
  }
  
  public void testFail() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String text = "<h1>This is a test</h1>";
    String text2 = "<h2>This is a test</h2>";
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getText")
                .withNoArguments()
                .will(returnValue(text));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "withValue", text2));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attrs);
    mti.setElement("hasText");
    
    HasTextTag tag = new HasTextTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("Titles should not match, and an exception should have been thrown.");
    }
    catch(HttpAssertionFailedException e) {
    }
  }
}
