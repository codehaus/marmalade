/* Created on Apr 22, 2004 */
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
public class IsHtmlTagTest extends AbstractTagCGLibTestCase{
  
  public void testSucceed() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("isHTML")
                .withNoArguments()
                .will(returnValue(true));
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Mock attrMock = attributesEmpty();
    
    IsHtmlTag tag = new IsHtmlTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("isHtml", (Attributes)attrMock.proxy());
    tag.end("isHtml");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
  }

  public void testFail() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("isHTML")
                .withNoArguments()
                .will(returnValue(false));
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Mock attrMock = attributesEmpty();
    
    IsHtmlTag tag = new IsHtmlTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("isHtml", (Attributes)attrMock.proxy());
    tag.end("isHtml");
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("Should have caught an assertion exception.");
    }
    catch(HttpAssertionFailedException e) {
      
    }
    
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
  }

}
