/* Created on Apr 25, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestResult;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.defaults.DefaultContext;
import org.codehaus.marmalade.testing.AbstractTagCGLibTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.cglib.Mock;


/**
 * @author jdcasey
 */
public class HttpTestSuiteTagTest extends AbstractTagCGLibTestCase{

  public void testSuccess() throws TagException, TagalogParseException, MarmaladeExecutionException {
    DummyTC tc = new DummyTC(false, false);
    
    Mock attrMock = attributesWithSingleAttribute("var", "testResult");
    
    HttpTestSuiteTag tag = new HttpTestSuiteTag();
    tag.begin("testSuite", (Attributes)attrMock.proxy());
    tag.addTest(tc);
    tag.end("testSuite");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    TestResult result = (TestResult)ctx.getVariable("testResult", null);
    assertNotNull("Context should contain test result.", result);
    
    Enumeration errors = result.errors();
    for(; errors.hasMoreElements(); ) {
      System.out.println("Error: " + errors.nextElement());
    }
    
    Enumeration failures = result.failures();
    for(; failures.hasMoreElements(); ) {
      System.out.println("Failure: " + failures.nextElement());
    }
    
    assertEquals("Should have 0 errors", 0, result.errorCount());
    assertEquals("Should have 0 failures", 0, result.failureCount());
    assertEquals("Should have 1 run", 1, result.runCount());
    assertTrue("Test Result should have passed", result.wasSuccessful());
    
    attrMock.verify();
  }

  public void testFailure() throws TagException, TagalogParseException, MarmaladeExecutionException {
    DummyTC tc = new DummyTC(false, true);
    
    Mock attrMock = attributesWithSingleAttribute("var", "testResult");
    
    HttpTestSuiteTag tag = new HttpTestSuiteTag();
    tag.begin("testSuite", (Attributes)attrMock.proxy());
    tag.addTest(tc);
    tag.end("testSuite");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    TestResult result = (TestResult)ctx.getVariable("testResult", null);
    assertNotNull("Context should contain test result.", result);
    
    Enumeration errors = result.errors();
    for(; errors.hasMoreElements(); ) {
      System.out.println("Error: " + errors.nextElement());
    }
    
    Enumeration failures = result.failures();
    for(; failures.hasMoreElements(); ) {
      System.out.println("Failure: " + failures.nextElement());
    }
    
    assertEquals("Should have 0 errors", 0, result.errorCount());
    assertEquals("Should have 1 failures", 1, result.failureCount());
    assertEquals("Should have 1 run", 1, result.runCount());
    assertFalse("Test Result should NOT have passed", result.wasSuccessful());
    
    attrMock.verify();
  }

  public void testError() throws TagException, TagalogParseException, MarmaladeExecutionException {
    DummyTC tc = new DummyTC(true, false);
    
    Mock attrMock = attributesWithSingleAttribute("var", "testResult");
    
    HttpTestSuiteTag tag = new HttpTestSuiteTag();
    tag.begin("testSuite", (Attributes)attrMock.proxy());
    tag.addTest(tc);
    tag.end("testSuite");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    TestResult result = (TestResult)ctx.getVariable("testResult", null);
    assertNotNull("Context should contain test result.", result);
    
    Enumeration errors = result.errors();
    for(; errors.hasMoreElements(); ) {
      System.out.println("Error: " + errors.nextElement());
    }
    
    Enumeration failures = result.failures();
    for(; failures.hasMoreElements(); ) {
      System.out.println("Failure: " + failures.nextElement());
    }
    
    assertEquals("Should have 1 errors", 1, result.errorCount());
    assertEquals("Should have 0 failures", 0, result.failureCount());
    assertEquals("Should have 1 run", 1, result.runCount());
    assertFalse("Test Result should NOT have passed", result.wasSuccessful());
    
    attrMock.verify();
  }



  public static final class DummyTC extends TestCase{
    private boolean error = false;
    private boolean fail = false;
    
    DummyTC(boolean error, boolean fail){
      super("testMe");
      this.error = error;
      this.fail = fail;
    }
    
    public void testMe() {
      if(error) {
        throw new UnsupportedOperationException("Test Error");
      }
      else if(fail) {
        throw new AssertionFailedError("Test Failure");
      }
      else {
        return;
      }
    }
  }
}
