/* Created on May 24, 2004 */
package org.codehaus.marmalade.modelbuilder;


/**
 * @author jdcasey
 */
public class DefaultRawAttribute implements ModelBuilderAttribute{

  private String namespace;
  private String name;
  private String value;
  
  public DefaultRawAttribute(String namespace, String name, String value){
    this.namespace = namespace;
    this.name = name;
    this.value = value;
  }

  public String getName(){
    return name;
  }

  public String getNamespace(){
    return namespace;
  }

  public String getValue(){
    return value;
  }
}
