/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.modelbuilder;

import junit.framework.TestCase;

import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.modelbuilder.MarmaladeTaglibResolver;
import org.codehaus.marmalade.modelbuilder.strategy.TaglibDefinitionStrategy;


/**
 * @author jdcasey
 */
public class MarmaladeTaglibResolverTest extends TestCase{
  
  public void testShouldSucceedWithSingleSuccessfulResolver() {
    SuccessfulStrategy strategy = new SuccessfulStrategy();
    MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver(
      new TaglibDefinitionStrategy[] {strategy}
    );
    
    MarmaladeTagLibrary taglib = resolver.resolve("marmalade", "test");
    
    assertNotNull(taglib);
  }
  
  public void testShouldSucceedWithDoubleResolverAndSuccessAtEnd() {
    UnsuccessfulStrategy strategy = new UnsuccessfulStrategy();
    SuccessfulStrategy strategy2 = new SuccessfulStrategy();
    MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver(
      new TaglibDefinitionStrategy[] {strategy, strategy2}
    );
    
    MarmaladeTagLibrary taglib = resolver.resolve("marmalade", "test");
    
    assertNotNull(taglib);
  }
  
  public void testShouldSucceedWithDoubleResolverAndSuccessAtBeginning() {
    UnsuccessfulStrategy strategy2 = new UnsuccessfulStrategy();
    SuccessfulStrategy strategy = new SuccessfulStrategy();
    MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver(
      new TaglibDefinitionStrategy[] {strategy, strategy2}
    );
    
    MarmaladeTagLibrary taglib = resolver.resolve("marmalade", "test");
    
    assertNotNull(taglib);
  }
  
  public void testShouldFailWithSingleUnsuccessfulResolver() {
    UnsuccessfulStrategy strategy = new UnsuccessfulStrategy();
    MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver(
      new TaglibDefinitionStrategy[] {strategy}
    );
    
    MarmaladeTagLibrary taglib = resolver.resolve("marmalade", "test");
    
    assertNull(taglib);
  }
  
  public void testShouldFailWithDoubleUnsuccessfulResolver() {
    UnsuccessfulStrategy strategy = new UnsuccessfulStrategy();
    UnsuccessfulStrategy strategy2 = new UnsuccessfulStrategy();
    MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver(
      new TaglibDefinitionStrategy[] {strategy, strategy2}
    );
    
    MarmaladeTagLibrary taglib = resolver.resolve("marmalade", "test");
    
    assertNull(taglib);
  }
  
  
  public static class Taglib implements MarmaladeTagLibrary{
    
    public Taglib() {}

    public MarmaladeTag createTag(MarmaladeTagInfo tagInfo){
      return null;
    }
  }
  
  public static class UnsuccessfulStrategy implements TaglibDefinitionStrategy{
    
    UnsuccessfulStrategy(){}

    public MarmaladeTagLibrary resolve(String prefix, String taglib){
      return null;
    }
    
  }

  public static class SuccessfulStrategy implements TaglibDefinitionStrategy{
    
    SuccessfulStrategy(){}

    public MarmaladeTagLibrary resolve(String prefix, String taglib){
      return new Taglib();
    }
    
  }
  
}
