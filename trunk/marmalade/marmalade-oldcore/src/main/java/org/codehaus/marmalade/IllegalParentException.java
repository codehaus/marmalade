/* Created on Apr 10, 2004 */
package org.codehaus.marmalade;

/**
 * @author jdcasey
 */
public class IllegalParentException extends MarmaladeExecutionException {

  public IllegalParentException(String element, Class cls) {
    super("Parent of tag \'" + element + "\' must be of type: " + cls.getName());
  }

}
