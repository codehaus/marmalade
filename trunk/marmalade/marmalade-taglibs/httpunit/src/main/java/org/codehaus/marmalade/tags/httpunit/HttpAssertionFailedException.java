/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;


/**
 * @author jdcasey
 */
public class HttpAssertionFailedException extends RuntimeException{

  public HttpAssertionFailedException(){
  }

  public HttpAssertionFailedException(String message){
    super(message);
  }

  public HttpAssertionFailedException(Throwable cause){
    super(cause);
  }

  public HttpAssertionFailedException(String message, Throwable cause){
    super(message, cause);
  }

}
