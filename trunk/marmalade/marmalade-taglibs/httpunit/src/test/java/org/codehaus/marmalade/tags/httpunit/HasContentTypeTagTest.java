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
public class HasContentTypeTagTest extends AbstractTagCGLibTestCase{

  public void testSucceed() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String contentType = "text/html";
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getContentType")
                .withNoArguments()
                .will(returnValue(contentType));
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Mock attrMock = attributesWithSingleAttribute("withValue", contentType);
    
    HasContentTypeTag tag = new HasContentTypeTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasContentType", (Attributes)attrMock.proxy());
    tag.end("hasContentType");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    responseMock.verify();
    rtMock.verify();
    attrMock.verify();
  }
  
  public void testFail() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String contentType = "text/html";
    String contentType2 = "text/plain";
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getContentType")
                .withNoArguments()
                .will(returnValue(contentType));
    
    Mock rtMock = new Mock(WebResponseTag.class);
    rtMock.expects(once())
          .method("getResponse")
          .withNoArguments()
          .will(returnValue((WebResponse)responseMock.proxy()));
    
    Mock attrMock = attributesWithSingleAttribute("withValue", contentType2);
    
    HasContentTypeTag tag = new HasContentTypeTag();
    tag.setParent((Tag)rtMock.proxy());
    tag.begin("hasContentType", (Attributes)attrMock.proxy());
    tag.end("hasContentType");
    
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
}
