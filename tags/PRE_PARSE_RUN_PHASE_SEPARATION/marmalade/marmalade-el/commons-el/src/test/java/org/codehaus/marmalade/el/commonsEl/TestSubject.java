/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.el.commonsEl;


/**
 * @author jdcasey
 */
public class TestSubject{
  
  private String id;
  private TestSubjectName name = new TestSubjectName();

  public TestSubject(String id){
    this.id = id;
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public TestSubjectName getName() {
    return name;
  }
  
  public static class TestSubjectName{
    private String firstName;
    public String lastName;
    
    TestSubjectName(){}
    
    public void setFirstName(String name) {
      this.firstName = name;
    }
    
    public String getFirstName() {
      return firstName;
    }
  }

}
