/* Created on May 20, 2004 */
package org.codehaus.marmalade.modelbuilder.strategy;

import junit.framework.TestCase;

import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.modelbuilder.strategy.LiteralClassDefinitionStrategy;
import org.codehaus.marmalade.modelbuilder.strategy.test.TestTaglibWithCorrectConstructor;


/**
 * @author jdcasey
 */
public class LiteralClassDefinitionStrategyTest extends TestCase implements TaglibDefinitionStrategyTestTemplate{
  
  public void testShouldResolveTestTagLibrary() {
    LiteralClassDefinitionStrategy strat = new LiteralClassDefinitionStrategy();
    MarmaladeTagLibrary taglib = strat.resolve(
      "marmalade", 
      "org.codehaus.marmalade.modelbuilder.strategy.test.TestTaglibWithCorrectConstructor"
    );
    
    assertNotNull(taglib);
    assertEquals(TestTaglibWithCorrectConstructor.class, taglib.getClass());
  }

  public void testShouldReturnNullWithNonExistentTagLibrary() {
    LiteralClassDefinitionStrategy strat = new LiteralClassDefinitionStrategy();
    MarmaladeTagLibrary taglib = strat.resolve(
      "marmalade", 
      "org.codehaus.marmalade.tags.mytags.NonExistentTaglib"
    );
    
    assertNull(taglib);
  }

  public void testShouldReturnNullWithNonEmptyConstructorTagLibrary() {
    LiteralClassDefinitionStrategy strat = new LiteralClassDefinitionStrategy();
    MarmaladeTagLibrary taglib = strat.resolve(
      "marmalade", 
      "org.codehaus.marmalade.modelbuilder.strategy.test.NonEmptyConstructorTaglib"
    );
    
    assertNull(taglib);
  }

  public void testShouldReturnNullWithNonTagLibraryClass() {
    LiteralClassDefinitionStrategy strat = new LiteralClassDefinitionStrategy();
    MarmaladeTagLibrary taglib = strat.resolve(
      "marmalade", 
      "org.codehaus.marmalade.modelbuilder.strategy.test.NonTaglibClass"
    );
    
    assertNull(taglib);
  }

}
