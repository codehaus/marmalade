/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.modelbuilder;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.tagalog.Attributes;

/**
 * @author jdcasey
 */
public interface ModelBuilderAttributes {
  
  public Iterator iterator();

  public String getNamespace(String name);
  
  public String getValue(String name);

}
