/* Created on Apr 20, 2004 */
package org.codehaus.marmalade;

import org.codehaus.marmalade.tags.passthrough.PassThroughTagLibrary;
import org.codehaus.tagalog.FallbackTagLibraryResolver;
import org.codehaus.tagalog.TagLibrary;


/**
 * @author jdcasey
 */
public class MarmaladeFBTaglibResolver extends MarmaladeTaglibResolver 
                                       implements FallbackTagLibraryResolver
{
  
  public static final String DEFAULT_PREFIX = "marmalade";
  
  private PassThroughTagLibrary passThroughTaglib = new PassThroughTagLibrary();
  private String prefix;

  public MarmaladeFBTaglibResolver(){
  }

  public MarmaladeFBTaglibResolver(String prefix){
    super(prefix);
    this.prefix = prefix;
  }

  public MarmaladeFBTaglibResolver(boolean usePassThrough){
    super(usePassThrough);
  }

  public MarmaladeFBTaglibResolver(String prefix, boolean usePassThrough){
    super(prefix, usePassThrough);
    this.prefix = prefix;
  }

  public TagLibrary resolve(String taglib){
    if(!taglib.startsWith(prefix)) {return passThroughTaglib;}
    
    int colonIdx = taglib.indexOf(':');
    if(colonIdx < 0) {colonIdx = -1;}
    
    return super.resolve(taglib.substring(colonIdx + 1));
  }
  
}
