/* Created on May 18, 2004 */
package org.codehaus.marmalade.modelbuilder.strategy;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.modelbuilder.strategy.tld.tags.TldTagLibrary;
import org.codehaus.tagalog.ParserConfiguration;
import org.codehaus.tagalog.TagalogParseException;
import org.codehaus.tagalog.TagalogParser;
import org.codehaus.tagalog.sax.TagalogSAXParserFactory;
import org.xml.sax.SAXException;


/**
 * @author jdcasey
 */
public class PrefixedTldDefinitionStrategy implements TaglibDefinitionStrategy{

  private TagalogSAXParserFactory factory;

  public PrefixedTldDefinitionStrategy(){
  }

  public MarmaladeTagLibrary resolve(String prefix, String taglib){
    ClassLoader cloader = getClass().getClassLoader();
    MarmaladeTagLibrary tlib = null;
    
    InputStream tldStream = null;
    
    try{
      String tldFile = "META-INF/" + prefix + "/" + taglib + ".tld";
      tldStream = cloader.getResourceAsStream(tldFile);
      
      if(tldStream != null) {
        try{
          ensureTldParserInited();
          TagalogParser parser = factory.createParser(tldStream);
          tlib = (MarmaladeTagLibrary)parser.parse();
        }
        // Ignore these and return null.
        catch(ParserConfigurationException e){}
        catch(SAXException e) {}
        catch(TagalogParseException e){}
      }
    }
    finally {
      if(tldStream != null) {
        try {tldStream.close();} catch(IOException e) {}
      }
    }
    
    return tlib;
  }

  private void ensureTldParserInited(){
    ParserConfiguration config = new ParserConfiguration();
    config.setDefaultNamespace(TldTagLibrary.NS_URL);
    config.addTagLibrary(TldTagLibrary.NS_URL, new TldTagLibrary());
    
    this.factory = new TagalogSAXParserFactory(config);
  }

}
