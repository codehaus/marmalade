/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.modelbuilder;

import java.util.Iterator;

/**
 * @author jdcasey
 */
public interface ModelBuilderAttributes {
  
  Iterator iterator();

  String getNamespace(String name);
  
  String getValue(String name);
  
  String getValue(String namespace, String name);

}
