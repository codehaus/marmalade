/* Created on May 20, 2004 */
package org.codehaus.marmalade.modelbuilder.strategy;

/**
 * @author jdcasey
 */
public interface TaglibDefinitionStrategyTestTemplate{

  public abstract void testShouldResolveTestTagLibrary();

  public abstract void testShouldReturnNullWithNonExistentTagLibrary();

  public abstract void testShouldReturnNullWithNonEmptyConstructorTagLibrary();

  public abstract void testShouldReturnNullWithNonTagLibraryClass();
}