/* Created on May 18, 2004 */
package org.codehaus.marmalade.modelbuilder.strategy;

import org.codehaus.marmalade.model.MarmaladeTagLibrary;


/**
 * @author jdcasey
 */
public interface TaglibDefinitionStrategy{

  MarmaladeTagLibrary resolve(String prefix, String taglib);
}
