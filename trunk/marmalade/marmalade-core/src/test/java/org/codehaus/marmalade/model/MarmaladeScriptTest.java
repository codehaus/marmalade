/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class MarmaladeScriptTest extends TestCase{

  public void testShouldConstructWithAndRetrieveLocation(){
    MarmaladeScript script = new MarmaladeScript("test.mmld", new TestRootTag(null));
    assertEquals("test.mmld", script.getLocation());
  }

  public void testShouldExecute() throws MarmaladeExecutionException{
    TestRootTag root = new TestRootTag(null);
    MarmaladeScript script = new MarmaladeScript("test.mmld", root);
    script.execute(null);
    assertTrue(root.executed());
  }
  
  public static class TestRootTag extends AbstractMarmaladeTag{
    
    private boolean executed = false;

    TestRootTag(MarmaladeTagInfo tagInfo){
      super(tagInfo);
    }
    
    boolean executed() {
      return executed;
    }
    
    protected void doExecute(MarmaladeExecutionContext context) 
    throws MarmaladeExecutionException
    {
      this.executed = true;
    }
  }

}
