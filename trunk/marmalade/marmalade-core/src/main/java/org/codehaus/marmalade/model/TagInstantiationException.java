/* Created on May 18, 2004 */
package org.codehaus.marmalade.model;


/**
 * @author jdcasey
 */
public class TagInstantiationException extends RuntimeException{

  public TagInstantiationException(){
  }

  public TagInstantiationException(String message){
    super(message);
  }

  public TagInstantiationException(Throwable cause){
    super(cause);
    initCause(cause);
  }

  public TagInstantiationException(String message, Throwable cause){
    super(message, cause);
    initCause(cause);
  }

}
