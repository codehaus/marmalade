/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.modelbuilder;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

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
    String value = null;
    
    ModelBuilderAttribute attr = (ModelBuilderAttribute)parsedAttributes.get(name);
    if(attr != null) {
      value = attr.getValue();
    }
    return value;
  }

  public String getValue(String namespace, String name)
  {
    String value = null;
    
    ModelBuilderAttribute attr = (ModelBuilderAttribute)parsedAttributes.get(name);
    if(attr != null && namespace != null && namespace.equals(attr.getNamespace())) {
      value = attr.getValue();
    }
    
    return value;
  }

  public void addAttribute(ModelBuilderAttribute attribute){
    this.parsedAttributes.put(attribute.getName(), attribute);
  }
  
  public void addAttribute(String namespace, String name, String value) {
    this.parsedAttributes.put(name, new DefaultRawAttribute(namespace, name, value));
  }
  
}
