/* Created on Apr 12, 2004 */
package org.codehaus.marmalade;


/**
 * @author jdcasey
 */
public class TestWithDirectClassTaglib extends AbstractMarmaladeTagLibrary{

  public TestWithDirectClassTaglib(){
    registerTag("tag", TestWithDirectClassTag.class);
  }

}
