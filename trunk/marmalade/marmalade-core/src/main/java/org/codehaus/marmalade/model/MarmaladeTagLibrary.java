/* Created on Mar 24, 2004 */
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;

/** Represents a marmalade tag library. This is simply a place in which we can specify 
 * common behavior in the future.
 * 
 * @author John Casey
 */
public interface MarmaladeTagLibrary{
  
  MarmaladeTag createTag(MarmaladeTagInfo tagInfo);

}
