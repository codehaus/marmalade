/* Created on May 20, 2004 */
package org.codehaus.marmalade.modelbuilder;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.modelbuilder.DefaultRawAttribute;
import org.codehaus.marmalade.modelbuilder.DefaultRawAttributes;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagBuilder;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;


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
    DefaultRawAttributes ra = new DefaultRawAttributes();
    ra.addAttribute(new DefaultRawAttribute("testNS", "testAttribute", "testValue"));
    
    MarmaladeTagInfo taginfo = new MarmaladeTagInfo();
    taginfo.setAttributes(ra);
    
    assertEquals(ra, taginfo.getAttributes());
    assertEquals("testValue", taginfo.getAttributes().getValue("testAttribute"));
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

    public void setExpressionEvaluator( ExpressionEvaluator el )
    {
    }

    public ExpressionEvaluator getExpressionEvaluator()
    {
      return null;
    }
    
  }
  
}
