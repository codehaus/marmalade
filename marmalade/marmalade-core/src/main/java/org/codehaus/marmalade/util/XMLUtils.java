/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jdcasey
 */
public class XMLUtils {
  
  /** Utility class; deny construction.
   */
  private XMLUtils() {
  }
  
  public static String escapeXml(String src){
    String result = src.replaceAll("&", "&amp;") /* Do &amp; first, to keep from messing up the */
                       .replaceAll("<", "&lt;")  /* rest of the replacements. */
                       .replaceAll(">", "&gt;");
                       
    return result;
  }
  
  public static String unEscapeXml(String xml){
    String result = xml.replaceAll("&amp;", "&")
                       .replaceAll("&gt;", ">")
                       .replaceAll("&lt;", "<");
    return result;
  }

}
