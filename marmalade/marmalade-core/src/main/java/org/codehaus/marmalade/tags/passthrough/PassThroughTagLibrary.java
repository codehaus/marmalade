/* Created on May 25, 2004 */
package org.codehaus.marmalade.tags.passthrough;

import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;


/**
 * @author jdcasey
 */
public class PassThroughTagLibrary implements MarmaladeTagLibrary{

  public PassThroughTagLibrary(){
  }

  public MarmaladeTag createTag(MarmaladeTagInfo tagInfo){
    return new PassThroughTag(tagInfo);
  }

}
