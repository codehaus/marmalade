/* Created on Apr 11, 2004 */
package org.codehaus.marmalade;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.tagalog.ParserConfiguration;
import org.codehaus.tagalog.TagalogParseException;
import org.codehaus.tagalog.TagalogParser;
import org.codehaus.tagalog.sax.TagalogSAXParserFactory;
import org.xml.sax.SAXException;

/**
 * @author jdcasey
 */
public class MarmaladeUtils {
  
  /** Utilities class; deny construction.
   */
  private MarmaladeUtils() {
  }
  
  public static MarmaladeScript parse(File input)
  throws MarmaladeParseException
  {
    BufferedInputStream in = null;
    try{
      in = new BufferedInputStream(new FileInputStream(input));
      MarmaladeTag result = _parse(in, input.getAbsolutePath());
      
      // result is guaranteed not to be null, because MEE will be thrown from _parse() if so.
      return new MarmaladeScript(input.getAbsolutePath(), result);
    }
    catch(FileNotFoundException e){
      throw new MarmaladeParseException("Cannot find script file: " + input.getAbsolutePath(), e);
    }
    finally {
      if(in != null) {
        try { in.close(); } catch(IOException e) {}
      }
    }
  }
  
  public static MarmaladeScript parse(URL input)
  throws MarmaladeParseException
  {
    BufferedInputStream in = null;
    try{
      in = new BufferedInputStream(input.openStream());
      MarmaladeTag result = _parse(in, input.toExternalForm());
      
      // result is guaranteed not to be null, because MEE will be thrown from _parse() if so.
      return new MarmaladeScript(input.toExternalForm(), result);
    }
    catch(IOException e){
      throw new MarmaladeParseException("Cannot find script file: " + input.toExternalForm(), e);
    }
    finally {
      if(in != null) {
        try { in.close(); } catch(IOException e) {}
      }
    }
  }
  
  private static MarmaladeTag _parse(InputStream in, String location)
  throws MarmaladeParseException
  {
    MarmaladeTag result = null;
    try{
      ParserConfiguration config = new ParserConfiguration();
      config.addTagLibraryResolver(new MarmaladeTaglibResolver());
      
      TagalogSAXParserFactory factory = new TagalogSAXParserFactory(config);
      TagalogParser parser = factory.createParser(in);
      
      result = (MarmaladeTag)parser.parse();
    }
    catch(ParserConfigurationException e){
      throw new MarmaladeParseException("Problem with mis-configured ParserConfiguration.", e);
    }
    catch(SAXException e){
      throw new MarmaladeParseException(
        "Error parsing XML script at location: " + location, e
      );
    }
    catch(TagalogParseException e){
      throw new MarmaladeParseException(
        "Error parsing XML script at location: " + location, e
      );
    }
    
    if(result == null) {
      throw new MarmaladeParseException(
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
