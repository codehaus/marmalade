/* Created on May 18, 2004 */
package org.codehaus.marmalade.modelbuilder;

import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;


/**
 * @author jdcasey
 */
public interface MarmaladeTagBuilder{
  
  public MarmaladeTagLibrary getTagLibrary();
  
  public MarmaladeTagInfo getTagInfo();
  
  public MarmaladeTag build();

}
