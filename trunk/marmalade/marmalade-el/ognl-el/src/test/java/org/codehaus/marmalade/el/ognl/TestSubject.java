/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.el.ognl;


/**
 * @author jdcasey
 */
public class TestSubject{
  
  private String id;

  public TestSubject(String id){
    this.id = id;
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }

}
