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
public class RawAttributes {
  
  private Map parsedAttributes = new TreeMap();

  public RawAttributes(Attributes attributes) {
    int attrCount = attributes.getAttributeCount();
    for(int i=0; i < attrCount; i++) {
      String name = attributes.getName(i);
      String ns = attributes.getNamespaceUri(i);
      String value = attributes.getValue(i);
      
      RawAttribute attr = new RawAttribute(
        ns, name, value
      );
      parsedAttributes.put(name, attr);
    }
    
    parsedAttributes = Collections.unmodifiableMap(parsedAttributes);
  }
  
  public Iterator iterator() {
    return parsedAttributes.values().iterator();
  }

  public String getNamespace(String name)
  {
    RawAttribute attr = (RawAttribute)parsedAttributes.get(name);
    String ns = attr.getNamespace();
    return ns;
  }
  
  public String getValue(String name)
  {
    RawAttribute attr = (RawAttribute)parsedAttributes.get(name);
    String value = attr.getValue();
    return value;
  }
  
  public static final class RawAttribute{
    private String namespace;
    private String name;
    private String value;
    
    RawAttribute(String namespace, String name, String value){
      this.namespace = namespace;
      this.name = name;
      this.value = value;
    }
    /**
     * @return Returns the name.
     */
    public String getName(){
      return name;
    }
    /**
     * @return Returns the namespace.
     */
    public String getNamespace(){
      return namespace;
    }
    /**
     * @return Returns the value.
     */
    public String getValue(){
      return value;
    }
  }

}
