/* Created on May 18, 2004 */
package org.codehaus.marmalade.modelbuilder;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.modelbuilder.strategy.LiteralClassDefinitionStrategy;
import org.codehaus.marmalade.modelbuilder.strategy.PrefixedDefFileDefinitionStrategy;
import org.codehaus.marmalade.modelbuilder.strategy.PrefixedTldDefinitionStrategy;
import org.codehaus.marmalade.modelbuilder.strategy.TaglibDefinitionStrategy;


/**
 * @author jdcasey
 */
public class MarmaladeTaglibResolver{
  
  public static final TaglibDefinitionStrategy[] DEFAULT_STRATEGY_CHAIN = {
    new LiteralClassDefinitionStrategy(), 
    new PrefixedTldDefinitionStrategy(), 
    new PrefixedDefFileDefinitionStrategy()
  };
  
  private TaglibDefinitionStrategy[] strategies;
  
  public MarmaladeTaglibResolver(TaglibDefinitionStrategy[] strategies) {
    this.strategies = strategies;
  }
  
  public MarmaladeTagLibrary resolve(String prefix, String taglib) {
    MarmaladeTagLibrary tlib = null;
    
    for(int i = 0; i < strategies.length; i++){
      TaglibDefinitionStrategy strategy = strategies[i];
      tlib = strategy.resolve(prefix, taglib);
      if(tlib != null) {
        break;
      }
    }
    
    return tlib;
  }

}
