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
public class HasTextPatternTagTest extends AbstractTagCGLibTestCase{

  public void testSucceed() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String text = "<h1>This is a test</h1>";
    String pattern = "<h1>.*</h1>";
    
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
    
    Mock attrMock = attributesWithSingleAttribute("withPattern", pattern);
    
    HasTextPatternTag tag = new HasTextPatternTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasTextPattern", (Attributes)attrMock.proxy());
    tag.end("hasTextPattern");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
  }
  
  public void testFail() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String text = "<h1>This is a test</h1>";
    String pattern = ".*NO MATCH.*";
    
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
    
    Mock attrMock = attributesWithSingleAttribute("withPattern", pattern);
    
    HasTextPatternTag tag = new HasTextPatternTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasTextPattern", (Attributes)attrMock.proxy());
    tag.end("hasTextPattern");
    
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
