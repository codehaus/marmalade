/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;
import org.codehaus.tagalog.Tag;


/**
 * @author jdcasey
 */
public class HttpTestTag extends AbstractMarmaladeTag{
  
  public static final String NAME_ATTRIBUTE = "name";

  public HttpTestTag(){
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    String name = (String)requireTagAttribute(NAME_ATTRIBUTE, String.class, context);
    ScriptedTest test = new ScriptedTest(name, context);
    
    Tag parent = getParent();
    if(parent != null && (parent instanceof HttpTestSuiteTag)) {
      ((HttpTestSuiteTag)parent).addTest(test);
    }
  }
  
  public final class ScriptedTest extends TestCase{
    
    private MarmaladeExecutionContext context;
    
    ScriptedTest(String name, MarmaladeExecutionContext context){
      super(name);
      this.context = context;
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
