/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;


/**
 * @author jdcasey
 */
public class HttpTestSuiteTag extends AbstractMarmaladeTag{
  
  public static final String VAR_ATTRIBUTE = "var";

  private TestSuite suite = new TestSuite();
  
  public HttpTestSuiteTag(){
  }
  
  public void addTest(TestCase test) {
    suite.addTest(test);
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    TestResult result = new TestResult();
    suite.run(result);
    
    String var = (String)getAttributes().getValue(VAR_ATTRIBUTE, String.class, context);
    if(var != null && var.length() > 0) {
      context.setVariable(var, result);
    }
  }
}
