/* Created on May 25, 2004 */
package org.codehaus.marmalade.parsetime;


/**
 * @author jdcasey
 */
public class EmptyScriptException extends MarmaladeParsetimeException{

  public EmptyScriptException(String message){
    super(message);
  }

  public EmptyScriptException(Throwable cause){
    super(cause);
  }

}
