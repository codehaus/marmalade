/* Created on Apr 10, 2004 */
package org.codehaus.marmalade;

/**
 * @author jdcasey
 */
public class MissingAttributeException extends MarmaladeExecutionException {

  public MissingAttributeException(String element, String attribute) {
    super("Tag \'" + element + "\' requires attribute \'" + attribute + "\'");
  }

}
