/* Created on Apr 12, 2004 */
package org.codehaus.marmalade;

import org.codehaus.marmalade.abstractions.*;


/**
 * @author jdcasey
 */
public class TestWithDefTaglib extends AbstractMarmaladeTagLibrary{

  /**
   * 
   */
  public TestWithDefTaglib(){
    registerTag("tag", TestWithDefTag.class);
  }

}