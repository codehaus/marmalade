/* Created on May 8, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.MockObjectTestCase;
import org.jmock.cglib.Mock;

import com.meterware.httpunit.WebLink;


/**
 * @author jdcasey
 */
public class AbstractAssertionTagTest extends MockObjectTestCase{
  
  public void testShouldSucceedOnPositiveTestResult() throws TagException, TagalogParseException, MarmaladeExecutionException{
    TestLinkSubTag tlst = new TestLinkSubTag(true);
    
    DefaultContext ctx = new DefaultContext();
    tlst.execute(ctx);
  }
  
  public void testShouldFailWithHttpAssertionFailedExceptionOnNegativeTestResult() throws TagException, TagalogParseException, MarmaladeExecutionException{
    TestLinkSubTag tlst = new TestLinkSubTag(false);
    
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
      super(null);
      this.shouldSucceed = shouldSucceed;
    }

    protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
      return shouldSucceed;
    }
    
  }

}
