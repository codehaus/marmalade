/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.parsetime;



/**
 * @author jdcasey
 */
public class MarmaladeParsetimeException extends Exception{
  
  public static final String MESSAGE_KEY = "marmalade script parse failed";

  public MarmaladeParsetimeException(String message){
    super(message);
  }

  public MarmaladeParsetimeException(Throwable cause){
    super(MESSAGE_KEY, cause);
  }

}
