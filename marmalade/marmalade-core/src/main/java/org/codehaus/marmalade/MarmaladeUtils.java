/* Created on Apr 11, 2004 */
package org.codehaus.marmalade;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;

/**
 * @author jdcasey
 */
public class MarmaladeUtils {

  /** Utilities class; deny construction.
   */
  private MarmaladeUtils() {
  }
  
  public static MarmaladeTag parse(File input)
  throws IOException, TagalogParseException, TagException
  {
    BufferedInputStream in = null;
    MarmaladeTag result = null;
    try{
      in = new BufferedInputStream(new FileInputStream(input));
    }
    finally {
      if(in != null) {
        try { in.close(); } catch(IOException e) {}
      }
    }
    
    return result;
  }
  
}
