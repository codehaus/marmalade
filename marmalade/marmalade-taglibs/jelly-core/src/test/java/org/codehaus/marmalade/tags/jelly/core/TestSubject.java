/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.tags.jelly.core;


/**
 * @author jdcasey
 */
public class TestSubject{
  
  private TestAttribute attribute;

  public TestSubject(TestAttribute attribute){
    this.attribute = attribute;
  }
  
  public TestSubject() {
  }
  
  public void setAttribute(TestAttribute attribute) {
    this.attribute = attribute;
  }
  
  public TestAttribute getAttribute() {
    return attribute;
  }

}
