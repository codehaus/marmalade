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
public class DefaultRawAttributes implements ModelBuilderAttributes{
  
  private Map parsedAttributes = new TreeMap();

  public Iterator iterator() {
    return parsedAttributes.values().iterator();
  }

  public String getNamespace(String name)
  {
    ModelBuilderAttribute attr = (ModelBuilderAttribute)parsedAttributes.get(name);
    String ns = attr.getNamespace();
    return ns;
  }
  
  public String getValue(String name)
  {
    ModelBuilderAttribute attr = (ModelBuilderAttribute)parsedAttributes.get(name);
    String value = attr.getValue();
    return value;
  }

  public void addAttribute(ModelBuilderAttribute attribute){
    this.parsedAttributes.put(attribute.getName(), attribute);
  }
  
}
