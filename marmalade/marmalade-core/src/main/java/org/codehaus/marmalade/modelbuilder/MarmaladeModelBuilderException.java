/* Created on May 25, 2004 */
package org.codehaus.marmalade.modelbuilder;


/**
 * @author jdcasey
 */
public abstract class MarmaladeModelBuilderException extends Exception{

  protected MarmaladeModelBuilderException(){
  }

  protected MarmaladeModelBuilderException(String message){
    super(message);
  }

  protected MarmaladeModelBuilderException(Throwable cause){
    super(cause);
  }

  protected MarmaladeModelBuilderException(String message, Throwable cause){
    super(message, cause);
  }

}
