/* Created on Apr 12, 2004 */
package org.codehaus.marmalade;

import org.codehaus.marmalade.tld.TldDefinedTagLibrary;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagLibrary;

import junit.framework.TestCase;


/**
 * @author jdcasey
 */
public class MarmaladeTaglibResolverTest extends TestCase{

  /*
   * Class to test for void MarmaladeTaglibResolver()
   */
  public void testMarmaladeTaglibResolver(){
    MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver();
    assertEquals(
      "Default taglib prefix should be used.", 
      MarmaladeTaglibResolver.DEFAULT_MARMALADE_TAGLIB_PREFIX, 
      resolver.uriPrefix()
    );
  }

  /*
   * Class to test for void MarmaladeTaglibResolver(String)
   */
  public void testMarmaladeTaglibResolverString(){
    String prefix = "jelly";
    
    MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver(prefix);
    assertEquals(
      "Custom taglib prefix should be used.", 
      prefix,
      resolver.uriPrefix()
    );
  }

  public void testResolveFromDirectClass(){
    String taglibName = TestWithDirectClassTaglib.class.getName();
    MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver();
    TagLibrary tlib = resolver.resolve(taglibName);
    assertNotNull("Resolved tag library from direct-class method should be non-null", tlib);
    assertTrue(
      "Resolved taglib from direct-class method should be of type TestWithDirectClassTaglib", 
      tlib instanceof TestWithDirectClassTaglib
    );
  }

  public void testResolveFromTLD(){
    String taglibName = "testWithTld";
    MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver();
    TagLibrary tlib = resolver.resolve(taglibName);
    assertNotNull("Resolved tag library from tld method should be non-null", tlib);
    assertTrue(
      "Resolved taglib from tld method should be of type TldDefinedTagLibrary", 
      tlib instanceof TldDefinedTagLibrary
    );
    
    Tag tag = tlib.getTag("tag");
    assertTrue(
      "Tag registered to element \'tag\' for tld library should be of type TestWithTldTag", 
      tag instanceof TestWithTldTag
    );
  }

  public void testResolveFromDef(){
    String taglibName = "testWithDef";
    MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver();
    TagLibrary tlib = resolver.resolve(taglibName);
    assertNotNull("Resolved tag library from def-file method should be non-null", tlib);
    assertTrue(
      "Resolved taglib from def-file method should be of type TestWithDefTaglib", 
      tlib instanceof TestWithDefTaglib
    );
  }

}
