/* Created on May 20, 2004 */
package org.codehaus.marmalade.modelbuilder.strategy;

import junit.framework.TestCase;

import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.modelbuilder.strategy.PrefixedDefFileDefinitionStrategy;
import org.codehaus.marmalade.modelbuilder.strategy.test.TestTaglibWithCorrectConstructor;


/**
 * @author jdcasey
 */
public class PrefixedDefFileDefinitionStrategyTest extends TestCase implements TaglibDefinitionStrategyTestTemplate{

  public void testShouldResolveTestTagLibrary() {
    PrefixedDefFileDefinitionStrategy strat = new PrefixedDefFileDefinitionStrategy();
    MarmaladeTagLibrary taglib = strat.resolve(
      "marmalade", 
      "testlib"
    );
    
    assertNotNull(taglib);
    assertEquals(TestTaglibWithCorrectConstructor.class, taglib.getClass());
  }

  public void testShouldReturnNullWithNonExistentTagLibrary(){
    PrefixedDefFileDefinitionStrategy strat = new PrefixedDefFileDefinitionStrategy();
    MarmaladeTagLibrary taglib = strat.resolve(
      "marmalade", 
      "testlib-with-nonexistent-class"
    );
    
    assertNull(taglib);
  }

  public void testShouldReturnNullWithNonEmptyConstructorTagLibrary(){
    PrefixedDefFileDefinitionStrategy strat = new PrefixedDefFileDefinitionStrategy();
    MarmaladeTagLibrary taglib = strat.resolve(
      "marmalade", 
      "testlib-with-nonempty-constructor"
    );
    
    assertNull(taglib);
  }

  public void testShouldReturnNullWithNonTagLibraryClass(){
    PrefixedDefFileDefinitionStrategy strat = new PrefixedDefFileDefinitionStrategy();
    MarmaladeTagLibrary taglib = strat.resolve(
      "marmalade", 
      "testlib-with-nontaglib"
    );
    
    assertNull(taglib);
  }

}
