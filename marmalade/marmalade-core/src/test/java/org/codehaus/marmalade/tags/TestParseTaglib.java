/* Created on May 25, 2004 */
package org.codehaus.marmalade.tags;

import org.codehaus.marmalade.model.AbstractMarmaladeTagLibrary;


/**
 * @author jdcasey
 */
public class TestParseTaglib extends AbstractMarmaladeTagLibrary{

  public TestParseTaglib(){
    registerTag("testTag", TestParseTag.class);
  }

}
