/* Created on Apr 12, 2004 */
package org.codehaus.marmalade;



/**
 * @author jdcasey
 */
public class TestParseTaglib extends AbstractMarmaladeTagLibrary{

  public TestParseTaglib(){
    registerTag("testTag", TestParseTag.class);
  }

}
