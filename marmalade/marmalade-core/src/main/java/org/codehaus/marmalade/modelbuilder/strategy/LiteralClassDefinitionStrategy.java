/* Created on May 18, 2004 */
package org.codehaus.marmalade.modelbuilder.strategy;

import org.codehaus.marmalade.model.MarmaladeTagLibrary;


/**
 * @author jdcasey
 */
public class LiteralClassDefinitionStrategy implements TaglibDefinitionStrategy{

  public LiteralClassDefinitionStrategy(){
  }

  public MarmaladeTagLibrary resolve(String prefix, String taglib){
    ClassLoader cloader = getClass().getClassLoader();
    MarmaladeTagLibrary tlib = null;
    
    try {
      Class tlCls = cloader.loadClass(taglib);
      if(MarmaladeTagLibrary.class.isAssignableFrom(tlCls)) {
        tlib = (MarmaladeTagLibrary)tlCls.newInstance();
      }
    }
    // Ignore these, and return null.
    catch (InstantiationException e) {}
    catch (IllegalAccessException e) {}
    catch (ClassNotFoundException e) {}
    
    return tlib;
  }

}
