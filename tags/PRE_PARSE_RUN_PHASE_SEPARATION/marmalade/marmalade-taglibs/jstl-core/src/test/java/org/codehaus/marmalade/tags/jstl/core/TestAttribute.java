/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.tags.jstl.core;


/**
 * @author jdcasey
 */
public class TestAttribute{
  
  private String value;

  public TestAttribute(String value){
    this.value = value;
  }
  
  public TestAttribute() {
  }
  
  public void setValue(String value) {
    this.value = value;
  }
  
  public String getValue() {
    return value;
  }

}
