/* Created on Apr 25, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.defaults.DefaultContext;
import org.codehaus.marmalade.tags.httpunit.structure.WebResponseTag;
import org.codehaus.marmalade.testing.AbstractTagCGLibTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.cglib.Mock;

import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class HasTextTagTest extends AbstractTagCGLibTestCase{

  public void testSucceedFullMatch() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String text = "<h1>This is a test</h1>";
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getText")
                .withNoArguments()
                .will(returnValue(text));
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Mock attrMock = attributesWithSingleAttribute("withValue", text);
    
    HasTextTag tag = new HasTextTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasText", (Attributes)attrMock.proxy());
    tag.end("hasText");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
  }
  
  public void testSucceedSubstring() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String text = "<h1>This is a test</h1>";
    String text2 = "This is";
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getText")
                .withNoArguments()
                .will(returnValue(text));
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Mock attrMock = attributesWithSingleAttribute("withValue", text2);
    
    HasTextTag tag = new HasTextTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasText", (Attributes)attrMock.proxy());
    tag.end("hasText");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
  }
  
  public void testFail() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String text = "<h1>This is a test</h1>";
    String text2 = "<h2>This is a test</h2>";
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getText")
                .withNoArguments()
                .will(returnValue(text));
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Mock attrMock = attributesWithSingleAttribute("withValue", text2);
    
    HasTextTag tag = new HasTextTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasText", (Attributes)attrMock.proxy());
    tag.end("hasText");
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("Titles should not match, and an exception should have been thrown.");
    }
    catch(HttpAssertionFailedException e) {
    }
    
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
  }
}
