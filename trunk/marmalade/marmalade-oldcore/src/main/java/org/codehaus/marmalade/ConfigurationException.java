/* Created on Mar 24, 2004 */
package org.codehaus.marmalade;

/** Represents an error while executing a marmalade process, which is related to a 
 * misconfiguration of the marmalade environment.
 * 
 * @author John Casey
 */
public class ConfigurationException extends MarmaladeExecutionException {

  /** Create a new exception instance
   * @param message The error message
   */
  public ConfigurationException(String message) {
    super(message);
  }

  /** Create a new exception instance
   * @param message The error message
   * @param cause The root cause which interrupted marmalade processing
   */
  public ConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }

}
