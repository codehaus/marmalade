/* Created on May 20, 2004 */
package org.codehaus.marmalade.modelbuilder.strategy.test;

import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;


/**
 * @author jdcasey
 */
public class NonEmptyConstructorTaglib implements MarmaladeTagLibrary{

  public NonEmptyConstructorTaglib(String id){
  }

  public MarmaladeTag createTag(MarmaladeTagInfo tagInfo){
    return null;
  }

}
