/* Created on May 25, 2004 */
package org.codehaus.marmalade.parsetime;


/**
 * @author jdcasey
 */
public class MarmaladeScriptNotFoundException extends MarmaladeParsetimeException{
  
  public static final String MESSAGE_KEY = "marmalade script not found";
  private String filename;

  public MarmaladeScriptNotFoundException(String filename){
    super(MESSAGE_KEY);
    this.filename = filename;
  }

  public String getFilename(){
    return filename;
  }
}
