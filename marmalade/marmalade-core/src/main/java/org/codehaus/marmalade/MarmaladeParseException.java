/* Created on Apr 12, 2004 */
package org.codehaus.marmalade;


/**
 * @author jdcasey
 */
public class MarmaladeParseException extends Exception{

  public MarmaladeParseException(String message){
    super(message);
  }

  public MarmaladeParseException(String message, Throwable cause){
    super(message, cause);
  }

}
