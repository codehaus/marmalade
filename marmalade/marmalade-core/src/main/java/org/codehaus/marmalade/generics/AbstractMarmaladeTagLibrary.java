/* Created on Mar 24, 2004 */
package org.codehaus.marmalade.generics;

import org.codehaus.marmalade.MarmaladeTagLibrary;
import org.codehaus.tagalog.AbstractTagLibrary;

/** Represents base-level common functionality for all marmalade tag libraries.
 * 
 * @author John Casey
 */
public abstract class AbstractMarmaladeTagLibrary extends AbstractTagLibrary 
                                                  implements MarmaladeTagLibrary 
{

  /** Construct a new tag library instance
   */
  protected AbstractMarmaladeTagLibrary() {
  }

}
