/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.parsetime;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.marmalade.model.*;
import org.codehaus.marmalade.modelbuilder.*;
import org.codehaus.marmalade.parsetime.*;
import org.codehaus.tagalog.ParserConfiguration;
import org.codehaus.tagalog.TagalogParseException;
import org.codehaus.tagalog.TagalogParser;
import org.codehaus.tagalog.sax.TagalogSAXParserFactory;
import org.xml.sax.SAXException;

/**
 * @author jdcasey
 */
public class ScriptParser {
  
  private MarmaladeTaglibResolver marmaladeResolver;

  public ScriptParser(MarmaladeTaglibResolver marmaladeResolver) {
    this.marmaladeResolver = marmaladeResolver;
  }
  
  public MarmaladeScript parse(File input)
  throws MarmaladeParsetimeException
  {
    BufferedInputStream in = null;
    try{
      in = new BufferedInputStream(new FileInputStream(input));
      MarmaladeTagBuilder result = _parse(in, input.getAbsolutePath());
      
      // result is guaranteed not to be null, because MEE will be thrown from _parse() if so.
      return new MarmaladeScript(input.getAbsolutePath(), result.build());
    }
    catch(FileNotFoundException e){
      throw new MarmaladeParsetimeException("Cannot find script file: " + input.getAbsolutePath(), e);
    }
    finally {
      if(in != null) {
        try { in.close(); } catch(IOException e) {}
      }
    }
  }
  
  public MarmaladeScript parse(URL input)
  throws MarmaladeParsetimeException
  {
    BufferedInputStream in = null;
    try{
      in = new BufferedInputStream(input.openStream());
      MarmaladeTagBuilder result = _parse(in, input.toExternalForm());
      
      // result is guaranteed not to be null, because MEE will be thrown from _parse() if so.
      return new MarmaladeScript(input.toExternalForm(), result.build());
    }
    catch(IOException e){
      throw new MarmaladeParsetimeException("Cannot find script file: " + input.toExternalForm(), e);
    }
    finally {
      if(in != null) {
        try { in.close(); } catch(IOException e) {}
      }
    }
  }
  
  private MarmaladeTagBuilder _parse(InputStream in, String location)
  throws MarmaladeParsetimeException
  {
    MarmaladeTagBuilder result = null;
    try{
      ParserConfiguration config = new ParserConfiguration();
      
      config.addTagLibraryResolver(new TagalogTaglibResolver(
        TagalogTaglibResolver.DEFAULT_MARMALADE_TAGLIB_PREFIX, marmaladeResolver
      ));
      
      TagalogSAXParserFactory factory = new TagalogSAXParserFactory(config);
      TagalogParser parser = factory.createParser(in);
      
      result = (MarmaladeTagBuilder)parser.parse();
    }
    catch(ParserConfigurationException e){
      throw new MarmaladeParsetimeException("Problem with mis-configured ParserConfiguration.", e);
    }
    catch(SAXException e){
      throw new MarmaladeParsetimeException(
        "Error parsing XML script at location: " + location, e
      );
    }
    catch(TagalogParseException e){
      throw new MarmaladeParsetimeException(
        "Error parsing XML script at location: " + location, e
      );
    }
    
    if(result == null) {
      throw new MarmaladeParsetimeException(
        "Error parsing script at: " + 
        location + 
        ". Reason: resulting root tag was null."
      );
    }
    else {
      return result;
    }
    
  }
  
}
