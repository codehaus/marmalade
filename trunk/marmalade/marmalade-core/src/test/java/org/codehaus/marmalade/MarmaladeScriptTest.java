/* Created on Apr 12, 2004 */
package org.codehaus.marmalade;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;


/**
 * @author jdcasey
 */
public class MarmaladeScriptTest extends MockObjectTestCase{

  public void testGetLocation(){
    String location = "/etc";
    
    Mock tagMock = mock(MarmaladeTag.class);
    tagMock.expects(never());
    
    MarmaladeScript script = new MarmaladeScript(location, (MarmaladeTag)tagMock.proxy());
    
    assertEquals("Script location should come back unchanged.", location, script.getLocation());
    
    tagMock.verify();
  }

  public void testGetRoot(){
    String location = "/etc";
    
    Mock tagMock = mock(MarmaladeTag.class);
    
    MarmaladeTag root = (MarmaladeTag)tagMock.proxy();
    MarmaladeScript script = new MarmaladeScript(location, root);
    
    assertEquals("Mock tag should come back as the root of the script", root, script.getRoot());
  }

  public void testExecute() throws MarmaladeExecutionException{
    String location = "/etc";
    
    Mock ctxMock = mock(MarmaladeExecutionContext.class);
    
    Mock tagMock = mock(MarmaladeTag.class);
    tagMock.expects(once())
           .method("execute")
           .with(isA(MarmaladeExecutionContext.class))
           .isVoid();
    
    MarmaladeTag root = (MarmaladeTag)tagMock.proxy();
    MarmaladeExecutionContext ctx = (MarmaladeExecutionContext)ctxMock.proxy();
    
    MarmaladeScript script = new MarmaladeScript(location, root);
    
    script.execute(ctx);
    
    assertEquals("Mock tag should come back as the root of the script", root, script.getRoot());
    
    tagMock.verify();
  }

}
