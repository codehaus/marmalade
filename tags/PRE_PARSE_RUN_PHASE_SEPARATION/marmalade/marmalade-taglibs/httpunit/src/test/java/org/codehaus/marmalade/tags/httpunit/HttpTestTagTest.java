/* Created on Apr 23, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestResult;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.MarmaladeTag;
import org.codehaus.marmalade.defaults.DefaultContext;
import org.codehaus.marmalade.testing.AbstractTagCGLibTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.cglib.Mock;


/**
 * @author jdcasey
 */
public class HttpTestTagTest extends AbstractTagCGLibTestCase{
  
  public void testSuccess() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock childMock = new Mock(MarmaladeTag.class);
    childMock.expects(once())
             .method("execute")
             .with(isA(MarmaladeExecutionContext.class))
             .isVoid();
    
    Map attributes = new TreeMap();
    attributes.put("name", "UnitTest");
    attributes.put("var", "testResult");
    
    Mock attrMock = attributesFromMap(attributes);
    
    HttpTestTag tag = new HttpTestTag();
    tag.begin("test", (Attributes)attrMock.proxy());
    tag.child(childMock.proxy());
    tag.end("test");
    
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
    
    childMock.verify();
    attrMock.verify();
  }

  public void testFailure() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock childMock = new Mock(MarmaladeTag.class);
    childMock.expects(once())
             .method("execute")
             .with(isA(MarmaladeExecutionContext.class))
             .will(throwException(new HttpAssertionFailedException()));
    
    Map attributes = new TreeMap();
    attributes.put("name", "UnitTest");
    attributes.put("var", "testResult");
    
    Mock attrMock = attributesFromMap(attributes);
    
    HttpTestTag tag = new HttpTestTag();
    tag.begin("test", (Attributes)attrMock.proxy());
    tag.child(childMock.proxy());
    tag.end("test");
    
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
    
    childMock.verify();
    attrMock.verify();
  }

  public void testError() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock childMock = new Mock(MarmaladeTag.class);
    childMock.expects(once())
             .method("execute")
             .with(isA(MarmaladeExecutionContext.class))
             .will(throwException(new NullPointerException()));
    
    Map attributes = new TreeMap();
    attributes.put("name", "UnitTest");
    attributes.put("var", "testResult");
    
    Mock attrMock = attributesFromMap(attributes);
    
    HttpTestTag tag = new HttpTestTag();
    tag.begin("test", (Attributes)attrMock.proxy());
    tag.child(childMock.proxy());
    tag.end("test");
    
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
    
    childMock.verify();
    attrMock.verify();
  }

}
