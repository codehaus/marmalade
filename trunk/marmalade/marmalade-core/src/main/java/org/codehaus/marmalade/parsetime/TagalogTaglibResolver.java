/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.parsetime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.modelbuilder.*;
import org.codehaus.marmalade.modelbuilder.BuilderTagLibrary;
import org.codehaus.marmalade.modelbuilder.strategy.tld.tags.TldTagLibrary;
import org.codehaus.tagalog.FallbackTagLibraryResolver;
import org.codehaus.tagalog.ParserConfiguration;
import org.codehaus.tagalog.PrefixTagLibraryResolver;
import org.codehaus.tagalog.TagLibrary;
import org.codehaus.tagalog.TagLibraryResolver;
import org.codehaus.tagalog.TagalogParseException;
import org.codehaus.tagalog.TagalogParser;
import org.codehaus.tagalog.sax.TagalogSAXParserFactory;
import org.xml.sax.SAXException;

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
