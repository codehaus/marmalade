/* Created on Apr 20, 2004 */
package org.codehaus.marmalade.tags.passthrough;

import org.codehaus.marmalade.abstractions.AbstractMarmaladeTagLibrary;
import org.codehaus.tagalog.Tag;


/**
 * @author jdcasey
 */
public class PassThroughTagLibrary extends AbstractMarmaladeTagLibrary{

  public PassThroughTagLibrary(){
  }

  public Tag getTag(String element){
    return new PassThroughTag();
  }

  public String listUnreleasedTags(){
    return "";
  }
  
  public void releaseTag(String arg0, Tag arg1){
  }
}
