/* Created on May 20, 2004 */
package org.codehaus.marmalade.modelbuilder;

import java.util.Iterator;

import org.codehaus.marmalade.modelbuilder.DefaultRawAttribute;
import org.codehaus.marmalade.modelbuilder.DefaultRawAttributes;
import org.codehaus.marmalade.modelbuilder.ModelBuilderAttribute;

import junit.framework.TestCase;


/**
 * @author jdcasey
 */
public class DefaultRawAttributesTest extends TestCase{
  
  public void testShouldIterateOverTwoAttributes() {
    DefaultRawAttributes ra = new DefaultRawAttributes();
    ra.addAttribute(new DefaultRawAttribute("testNS", "testAttr", "testVal"));
    ra.addAttribute(new DefaultRawAttribute("testNS2", "testAttr2", "testVal2"));
    
    int counter = 0;
    for(Iterator it = ra.iterator(); it.hasNext();){
      ModelBuilderAttribute attribute = (ModelBuilderAttribute)it.next();
      counter++;
    }
    
    assertEquals(2, counter);
  }
  
  public void testShouldStoreRetrieveAttributeNamespace() {
    DefaultRawAttributes ra = new DefaultRawAttributes();
    ra.addAttribute(new DefaultRawAttribute("testNS", "testAttr", "testVal"));
    
    assertEquals("testNS", ra.getNamespace("testAttr"));
  }
  
  public void testShouldStoreRetrieveAttributeValue() {
    DefaultRawAttributes ra = new DefaultRawAttributes();
    ra.addAttribute(new DefaultRawAttribute("testNS", "testAttr", "testVal"));
    
    assertEquals("testVal", ra.getValue("testAttr"));
  }
  
}
