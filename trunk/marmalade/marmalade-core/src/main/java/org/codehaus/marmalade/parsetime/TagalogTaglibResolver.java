/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.parsetime;

import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.modelbuilder.BuilderTagLibrary;
import org.codehaus.marmalade.modelbuilder.MarmaladeTaglibResolver;
import org.codehaus.tagalog.FallbackTagLibraryResolver;
import org.codehaus.tagalog.PrefixTagLibraryResolver;
import org.codehaus.tagalog.TagLibrary;

/**
 * @author jdcasey
 */
public class TagalogTaglibResolver implements PrefixTagLibraryResolver, FallbackTagLibraryResolver
{
  
  public static final String DEFAULT_MARMALADE_TAGLIB_PREFIX = "marmalade";
  
  private String prefix = DEFAULT_MARMALADE_TAGLIB_PREFIX;
  
  private Map mappedTaglibs = new TreeMap();
  private MarmaladeTaglibResolver marmaladeResolver;

  public TagalogTaglibResolver(String prefix, MarmaladeTaglibResolver marmaladeResolver) {
    this.prefix = prefix;
    this.marmaladeResolver = marmaladeResolver;
  }

  public String uriPrefix() {
    return prefix;
  }
  
  public TagLibrary resolve(String tlib) {
    TagLibrary taglib = (TagLibrary)mappedTaglibs.get(tlib);
    if(taglib == null) {
      taglib = new BuilderTagLibrary(uriPrefix(), tlib, marmaladeResolver);
    }
    
    return taglib;
  }

  public void addTagLibrary(String uri, TagLibrary tagLibrary){
    mappedTaglibs.put(uri, tagLibrary);
  }

}
