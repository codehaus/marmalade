/* Created on May 25, 2004 */
package org.codehaus.marmalade.modelbuilder.strategy;

import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.tags.passthrough.PassThroughTagLibrary;


/**
 * @author jdcasey
 */
public class PassThroughTaglibDefinitionStrategy implements TaglibDefinitionStrategy{
  
  private PassThroughTagLibrary ptTaglib;

  public PassThroughTaglibDefinitionStrategy(){
    this.ptTaglib = new PassThroughTagLibrary();
  }

  public MarmaladeTagLibrary resolve(String prefix, String taglib){
    return ptTaglib;
  }

}
