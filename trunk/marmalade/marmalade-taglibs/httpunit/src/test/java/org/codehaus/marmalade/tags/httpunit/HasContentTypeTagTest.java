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
public class HasContentTypeTagTest extends MockObjectTestCase{

  public void testSucceed() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String contentType = "text/html";
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getContentType")
                .withNoArguments()
                .will(returnValue(contentType));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "withValue", contentType));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attrs);
    mti.setElement("hasContentType");
    
    HasContentTypeTag tag = new HasContentTypeTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
  }
  
  public void testFail() throws TagException, TagalogParseException, MarmaladeExecutionException {
    String contentType = "text/html";
    String contentType2 = "text/plain";
    
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("getContentType")
                .withNoArguments()
                .will(returnValue(contentType));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "withValue", contentType2));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(attrs);
    mti.setElement("hasContentType");
    
    HasContentTypeTag tag = new HasContentTypeTag(mti);
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
