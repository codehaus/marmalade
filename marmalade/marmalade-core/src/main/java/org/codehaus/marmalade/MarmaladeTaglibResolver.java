/* Created on Apr 11, 2004 */
package org.codehaus.marmalade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.marmalade.tld.tags.TldTagLibrary;
import org.codehaus.tagalog.FallbackTagLibraryResolver;
import org.codehaus.tagalog.ParserConfiguration;
import org.codehaus.tagalog.PrefixTagLibraryResolver;
import org.codehaus.tagalog.TagLibrary;
import org.codehaus.tagalog.TagalogParseException;
import org.codehaus.tagalog.TagalogParser;
import org.codehaus.tagalog.sax.TagalogSAXParserFactory;
import org.xml.sax.SAXException;

/**
 * @author jdcasey
 */
public class MarmaladeTaglibResolver implements PrefixTagLibraryResolver
{
  
  public static final String DEFAULT_MARMALADE_TAGLIB_PREFIX = "marmalade";
  
  private String prefix = DEFAULT_MARMALADE_TAGLIB_PREFIX;
  private Map resolved = new TreeMap();

  private TagalogSAXParserFactory factory;

  public MarmaladeTaglibResolver() {
  }

  public MarmaladeTaglibResolver(String prefix) {
    this.prefix = prefix;
  }

  public String uriPrefix() {
    return prefix;
  }

  public TagLibrary resolve(String taglib) {
    // NOTE: If the nested if statement ugliness below looks weird, I'm trying to reduce the 
    // processing overhead for calls that resolve early, rather than make it easy to read.
    //
    // TASKS: 
    // ---------------------------------------------------------------------------------------------
    // 1. Check for previously resolved taglib for prefix
    // 2. Check for taglib as literal TagLibrary class name
    // 3. Check for META-INF/<prefix>/<taglib>.tld
    // 4. Check for META-INF/<prefix>/<taglib>.def (contains single-line class name of taglib impl)
    // ---------------------------------------------------------------------------------------------
    
    // 1.
    TagLibrary impl = (TagLibrary)resolved.get(taglib);
    
    ClassLoader cloader = getClass().getClassLoader();
    
    // 2.
    if(impl == null) {
      try {
        Class tlCls = cloader.loadClass(taglib);
        impl = (TagLibrary)tlCls.newInstance();
      }
      // Ignore these, and move on to step 3.
      catch (InstantiationException e) {}
      catch (IllegalAccessException e) {}
      catch (ClassNotFoundException e) {}
      
      // 3.
      if(impl == null) {
        InputStream tldStream = null;
        
        try{
          tldStream = cloader.getResourceAsStream("META-INF/" + prefix + "/" + taglib + ".tld");
          
          if(tldStream != null) {
            try{
              ensureTldParserInited();
              TagalogParser parser = factory.createParser(tldStream);
              impl = (MarmaladeTagLibrary)parser.parse();
            }
            // Ignore these and move to step 4.
            catch(ParserConfigurationException e){}
            catch(SAXException e) {}
            catch(TagalogParseException e){}
          }
        }
        // Ignore and move to step 4.
//        catch(IOException e) {}
        finally {
          if(tldStream != null) {
            try {tldStream.close();} catch(IOException e) {}
          }
        }
        
        // 4.
        if(impl == null) {
          InputStream defStream = null;
          try{
            defStream = cloader.getResourceAsStream("META-INF/" + prefix + "/" + taglib + ".def");
            if(defStream != null) {
              String className = null;
              try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(defStream));
                className = reader.readLine();
                reader.close();
              }
              // Ignore this...next conditional will fall through if read unsuccessful.
              catch(IOException e ) {}
              
              if(className != null && className.length() > 0) {
                try {
                  Class tlCls = cloader.loadClass(className);
                  impl = (TagLibrary)tlCls.newInstance();
                }
                // Ignore these, and simply fail (return a null impl).
                catch (InstantiationException e) {}
                catch (IllegalAccessException e) {}
                catch (ClassNotFoundException e) {}
              }
            }
          }
          // We have no feedback mechanism, so fail silently.
//          catch(IOException e) {}
          finally {
            if(defStream != null) {
              try {defStream.close();} catch(IOException e) {}
            }
          }
        }
      }
    }
    
    if(impl != null) {
      resolved.put(taglib, impl);
    }
    
    return impl;
  }

  public void addTagLibrary(String uri, TagLibrary tagLibrary){
    if(!(tagLibrary instanceof MarmaladeTagLibrary)) {
      throw new IllegalArgumentException(
        "Non-Marmalade TagLibrary implementations cannot currently be used inside a marmalade script."
      );
    }
    else {
      resolved.put(uri, tagLibrary);
    }
  }

  private void ensureTldParserInited(){
    ParserConfiguration config = new ParserConfiguration();
    config.setDefaultNamespace(TldTagLibrary.NS_URL);
    config.addTagLibrary(TldTagLibrary.NS_URL, new TldTagLibrary());
    
    this.factory = new TagalogSAXParserFactory(config);
  }

}
