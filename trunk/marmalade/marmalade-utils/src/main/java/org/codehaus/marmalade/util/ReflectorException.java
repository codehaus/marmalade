/*

Copyright (c) 2002 John Casey. All rights reserved.

SEE licenses/cj-license.txt FOR MORE INFORMATION.

*/


/*
 * ReflectorException.java
 *
 * Created on November 1, 2002, 7:33 AM
 */

package org.codehaus.marmalade.util;

/** Exception indicating that an error has occurred while instantiating a 
 *  class with the Reflector class. This exception is meant to put a more
 *  user-friendly face on the myriad other exceptions throws during reflective
 *  object creation.
 *
 * @author  John Casey
 */
public class ReflectorException extends Exception {
  
  /** Create a new ReflectorException.
   */
  public ReflectorException() {
  }
  
  /** Create a new ReflectorException with the specified message.
   * @param msg The message.
   */
  public ReflectorException(String msg) {
    super(msg);
  }
  
  /** Create a new ReflectorException with the specified root cause.
   * @param root The root cause.
   */
  public ReflectorException(Throwable root) {
    super(root);
  }
  
  /** Create a new ReflectorException with the specified message and root cause.
   * @param msg The message.
   * @param root The root cause.
   */
  public ReflectorException(String msg, Throwable root) {
    super(msg, root);
  }
  
}