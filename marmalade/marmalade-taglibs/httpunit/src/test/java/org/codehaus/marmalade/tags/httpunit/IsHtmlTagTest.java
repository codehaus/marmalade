/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;

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
import org.jmock.core.MockObjectSupportTestCase;

import com.meterware.httpunit.WebResponse;


/**
 * @author jdcasey
 */
public class IsHtmlTagTest extends MockObjectTestCase{
  
  public void testSucceed() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("isHTML")
                .withNoArguments()
                .will(returnValue(true));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(new DefaultRawAttributes());
    mti.setElement("isHtml");
    
    IsHtmlTag tag = new IsHtmlTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
  }

  public void testFail() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock responseMock = new Mock(TestingWebResponse.class);
    responseMock.expects(once())
                .method("isHTML")
                .withNoArguments()
                .will(returnValue(false));
    
    Mock parentMock = new Mock(WebResponseTag.class);
    parentMock.expects(once())
              .method("getResponse")
              .withNoArguments()
              .will(returnValue(responseMock.proxy()));
    
    MarmaladeTagInfo mti = new MarmaladeTagInfo();
    mti.setAttributes(new DefaultRawAttributes());
    mti.setElement("isHtml");
    
    IsHtmlTag tag = new IsHtmlTag(mti);
    tag.setParent((MarmaladeTag)parentMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("Should have caught an assertion exception.");
    }
    catch(HttpAssertionFailedException e) {
      
    }
  }

}
