/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.runtime;


/**
 * @author jdcasey
 */
public class IllegalAncestorException extends MarmaladeExecutionException {

  public IllegalAncestorException(String element, Class cls) {
    super("Tag \'" + element + "\' must descend from ancestor tag of type: " + cls.getName());
  }

}
