/* Created on May 8, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;
import org.codehaus.marmalade.defaults.DefaultContext;
import org.codehaus.marmalade.testing.AbstractTagCGLibTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.cglib.Mock;

import com.meterware.httpunit.WebLink;


/**
 * @author jdcasey
 */
public class AbstractAssertionTagTest extends AbstractTagCGLibTestCase{
  
  public void testShouldSucceedOnPositiveTestResult() throws TagException, TagalogParseException, MarmaladeExecutionException{
    TestLinkSubTag tlst = new TestLinkSubTag(true);
    
    Mock aMock = attributesEmpty();
    tlst.begin("tlst", (Attributes)aMock.proxy());
    tlst.end("tlst");
    
    DefaultContext ctx = new DefaultContext();
    tlst.execute(ctx);
  }
  
  public void testShouldFailWithHttpAssertionFailedExceptionOnNegativeTestResult() throws TagException, TagalogParseException, MarmaladeExecutionException{
    TestLinkSubTag tlst = new TestLinkSubTag(false);
    
    Mock aMock = attributesEmpty();
    tlst.begin("tlst", (Attributes)aMock.proxy());
    tlst.end("tlst");
    
    DefaultContext ctx = new DefaultContext();
    try {
      tlst.execute(ctx);
      fail("Should have failed with HttpAssertionFailedException.");
    }
    catch(HttpAssertionFailedException e) {}
  }
  
  private static final class TestLinkSubTag extends AbstractAssertionTag{
    
    private boolean shouldSucceed;

    TestLinkSubTag(boolean shouldSucceed){
      this.shouldSucceed = shouldSucceed;
    }

    protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
      return shouldSucceed;
    }
    
  }

}
