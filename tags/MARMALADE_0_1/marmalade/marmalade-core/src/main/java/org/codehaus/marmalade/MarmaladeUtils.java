/* Created on Apr 11, 2004 */
package org.codehaus.marmalade;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    MarmaladeTag result = null;
    try{
      in = new BufferedInputStream(new FileInputStream(input));
      
      ParserConfiguration config = new ParserConfiguration();
      config.addTagLibraryResolver(new MarmaladeTaglibResolver());
      
      TagalogSAXParserFactory factory = new TagalogSAXParserFactory(config);
      TagalogParser parser = factory.createParser(in);
      
      result = (MarmaladeTag)parser.parse();
    }
    catch(FileNotFoundException e){
      throw new MarmaladeParseException("Cannot find script file: " + input.getAbsolutePath(), e);
    }
    catch(ParserConfigurationException e){
      throw new MarmaladeParseException("Problem with mis-configured ParserConfiguration.", e);
    }
    catch(SAXException e){
      throw new MarmaladeParseException(
        "Error parsing XML script in file: " + input.getAbsolutePath(), e
      );
    }
    catch(TagalogParseException e){
      throw new MarmaladeParseException(
        "Error parsing XML script in file: " + input.getAbsolutePath(), e
      );
    }
    finally {
      if(in != null) {
        try { in.close(); } catch(IOException e) {}
      }
    }
    
    if(result == null) {
      throw new MarmaladeParseException(
        "Error parsing script at: " + 
        input.getAbsolutePath() + 
        ". Reason: resulting root tag was null."
      );
    }
    else {
      return new MarmaladeScript(input.getAbsolutePath(), result);
    }
    
  }
  
}
