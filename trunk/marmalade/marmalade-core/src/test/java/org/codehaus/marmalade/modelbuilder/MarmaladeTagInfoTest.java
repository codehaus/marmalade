/* Created on May 20, 2004 */
package org.codehaus.marmalade.modelbuilder;

import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.tagalog.Attributes;

import junit.framework.TestCase;


/**
 * @author jdcasey
 */
public class MarmaladeTagInfoTest extends TestCase{
  
  public void testShouldStoreRetrieveScheme() {
    MarmaladeTagInfo taginfo = new MarmaladeTagInfo();
    taginfo.setScheme("marmalade");
    assertEquals("marmalade", taginfo.getScheme());
  }
  
  public void testShouldStoreRetrieveTaglib() {
    MarmaladeTagInfo taginfo = new MarmaladeTagInfo();
    taginfo.setTaglib("testlib");
    assertEquals("testlib", taginfo.getTaglib());
  }
  
  public void testShouldStoreRetrieveElement() {
    MarmaladeTagInfo taginfo = new MarmaladeTagInfo();
    taginfo.setElement("testElement");
    assertEquals("testElement", taginfo.getElement());
  }
  
  public void testShouldStoreRetrieveText() {
    MarmaladeTagInfo taginfo = new MarmaladeTagInfo();
    taginfo.appendText("test".toCharArray(), 0, "test".length());
    assertEquals("test", taginfo.getText());
  }
  
  public void testShouldStoreRetrieveParent() {
    TestBuilder tb = new TestBuilder();
    MarmaladeTagInfo taginfo = new MarmaladeTagInfo();
    taginfo.setParent(tb);
    assertEquals(tb, taginfo.getParent());
  }
  
  public void testShouldStoreRetrieveSingleChild() {
    TestBuilder tb = new TestBuilder();
    MarmaladeTagInfo taginfo = new MarmaladeTagInfo();
    taginfo.addChild(tb);
    
    assertEquals(1, taginfo.getChildren().size());
    assertTrue(taginfo.getChildren().contains(tb));
  }
  
  public void testShouldStoreRetrieveMultipleChildren() {
    TestBuilder tb = new TestBuilder();
    TestBuilder tb2 = new TestBuilder();
    MarmaladeTagInfo taginfo = new MarmaladeTagInfo();
    taginfo.addChild(tb);
    taginfo.addChild(tb2);
    
    assertEquals(2, taginfo.getChildren().size());
    assertTrue(taginfo.getChildren().contains(tb));
    assertTrue(taginfo.getChildren().contains(tb2));
  }
  
  public void testShouldStoreRetrieveAttributes() {
    TestAttributes ta = new TestAttributes();
    RawAttributes ra = new RawAttributes(ta);
    MarmaladeTagInfo taginfo = new MarmaladeTagInfo();
    taginfo.setAttributes(ra);
    
    assertEquals(ra, taginfo.getAttributes());
    assertEquals("testValue", taginfo.getAttributes().getValue("testAttribute"));
  }
  
  public static class TestAttributes implements Attributes{
    TestAttributes(){}

    public int getAttributeCount(){
      return 1;
    }

    public String getNamespaceUri(int attributeIndex){
      return "marmalade:testlib";
    }

    public String getName(int attributeIndex){
      return "testAttribute";
    }

    public String getValue(int attributeIndex){
      return "testValue";
    }

    public String getValue(String attributeName){
      return "testValue";
    }

    public String getValue(String namespaceUri, String attributeName){
      return "testValue";
    }
    
  }
  
  public static class TestBuilder implements MarmaladeTagBuilder{
    
    TestBuilder(){}

    public MarmaladeTagLibrary getTagLibrary(){
      return null;
    }

    public MarmaladeTagInfo getTagInfo(){
      return null;
    }

    public MarmaladeTag build(){
      return null;
    }
    
  }
  
}
