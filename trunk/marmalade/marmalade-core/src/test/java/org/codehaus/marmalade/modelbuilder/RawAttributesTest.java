/* Created on May 20, 2004 */
package org.codehaus.marmalade.modelbuilder;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.tagalog.Attributes;

import junit.framework.TestCase;


/**
 * @author jdcasey
 */
public class RawAttributesTest extends TestCase{
  
  public void testShouldIterateOverTwoAttributes() {
    TestAttributes ta = new TestAttributes();
    ta.addAttribute("testNS", "testAttr", "testVal");
    ta.addAttribute("testNS2", "testAttr2", "testVal2");
    
    RawAttributes ra = new RawAttributes(ta);
    
    int counter = 0;
    for(Iterator it = ra.iterator(); it.hasNext();){
      RawAttributes.RawAttribute attribute = (RawAttributes.RawAttribute)it.next();
      counter++;
    }
    
    assertEquals(2, counter);
  }
  
  public void testShouldStoreRetrieveAttributeNamespace() {
    TestAttributes ta = new TestAttributes();
    ta.addAttribute("testNS", "testAttr", "testVal");
    
    RawAttributes ra = new RawAttributes(ta);
    
    assertEquals("testNS", ra.getNamespace("testAttr"));
  }
  
  public void testShouldStoreRetrieveAttributeValue() {
    TestAttributes ta = new TestAttributes();
    ta.addAttribute("testNS", "testAttr", "testVal");
    
    RawAttributes ra = new RawAttributes(ta);
    
    assertEquals("testVal", ra.getValue("testAttr"));
  }
  
  public static class TestAttributes implements Attributes{
    
    private List attributes = new LinkedList();
    
    TestAttributes(){}
    
    void addAttribute(String namespace, String name, String value){
      attributes.add(new TestAttribute(namespace, name, value));
    }

    public int getAttributeCount(){
      return attributes.size();
    }

    public String getNamespaceUri(int attributeIndex){
      return ((TestAttribute)attributes.get(attributeIndex)).namespace;
    }

    public String getName(int attributeIndex){
      return ((TestAttribute)attributes.get(attributeIndex)).name;
    }

    public String getValue(int attributeIndex){
      return ((TestAttribute)attributes.get(attributeIndex)).value;
    }

    public String getValue(String attributeName){
      for(Iterator it = attributes.iterator(); it.hasNext();){
        TestAttribute attr = (TestAttribute)it.next();
        if(attr.name.equals(attributeName)) {
          return attr.value;
        }
      }
      return null;
    }

    public String getValue(String namespaceUri, String attributeName){
      for(Iterator it = attributes.iterator(); it.hasNext();){
        TestAttribute attr = (TestAttribute)it.next();
        if(attr.name.equals(attributeName)) {
          return attr.value;
        }
      }
      return null;
    }
    
  }
  
  public static class TestAttribute{
    String namespace;
    String name;
    String value;
    
    TestAttribute(String namespace, String name, String value){
      this.namespace = namespace;
      this.name = name;
      this.value = value;
    }
  }

}
