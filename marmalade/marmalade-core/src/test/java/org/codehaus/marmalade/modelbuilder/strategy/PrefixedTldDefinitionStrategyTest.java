/* Created on May 20, 2004 */
package org.codehaus.marmalade.modelbuilder.strategy;

import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.modelbuilder.strategy.tld.TldDefinedTagLibrary;

import junit.framework.TestCase;


/**
 * @author jdcasey
 */
public class PrefixedTldDefinitionStrategyTest extends TestCase {

  public void testShouldResolveTestTagLibrary(){
    PrefixedTldDefinitionStrategy strat = new PrefixedTldDefinitionStrategy();
    MarmaladeTagLibrary taglib = strat.resolve("marmalade", "testlib");
    
    assertNotNull(taglib);
    assertEquals(TldDefinedTagLibrary.class, taglib.getClass());
  }

  public void testShouldReturnNullWithNonExistentTLD(){
    PrefixedTldDefinitionStrategy strat = new PrefixedTldDefinitionStrategy();
    MarmaladeTagLibrary taglib = strat.resolve("marmalade", "testlib-missing");
    
    assertNull(taglib);
  }

}
