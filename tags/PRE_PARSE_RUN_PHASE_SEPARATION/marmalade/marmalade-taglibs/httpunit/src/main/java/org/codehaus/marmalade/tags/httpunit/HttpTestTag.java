/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestResult;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;
import org.codehaus.tagalog.Tag;


/**
 * @author jdcasey
 */
public class HttpTestTag extends AbstractMarmaladeTag{
  
  public static final String NAME_ATTRIBUTE = "name";
  public static final String VAR_ATTRIBUTE = "var";

  public HttpTestTag(){
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    String name = (String)requireTagAttribute(NAME_ATTRIBUTE, String.class, context);
    ScriptedTest test = new ScriptedTest(name, context);
    
    Tag parent = getParent();
    if(parent != null && (parent instanceof HttpTestSuiteTag)) {
      ((HttpTestSuiteTag)parent).addTest(test);
    }
    else {
      String var = (String)requireTagAttribute(VAR_ATTRIBUTE, String.class, context);
      TestResult result = test.run();
      context.setVariable(var, result);
    }
  }
  
  public final class ScriptedTest extends TestCase{
    
    private MarmaladeExecutionContext context;
    private String name;
    
    ScriptedTest(String name, MarmaladeExecutionContext context){
      super("testScriptedAssertions");
      this.context = context;
      this.name = name;
    }
    
    public String getName() {
      return name;
    }
    
    public void testScriptedAssertions() {
      try {
        processChildren(context);
      }
      catch(MarmaladeExecutionException e) {
        e.printStackTrace(context.getErrWriter());
        throw new AssertionFailedError("Test of scripted elements failed. Reason: " + e.getMessage());
      }
      catch(HttpAssertionFailedException e) {
        e.printStackTrace(context.getErrWriter());
        throw new AssertionFailedError("Test of scripted elements failed.\n\nReason: " + e.getMessage());
      }
    }
  }
  
}
