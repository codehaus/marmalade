/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.ant;

/**
 * @author jdcasey
 */
public class AntCompatUncheckedException extends RuntimeException {

    public AntCompatUncheckedException() {
    }

    public AntCompatUncheckedException(String message) {
        super(message);
    }

    public AntCompatUncheckedException(Throwable cause) {
        super(cause);
    }

    public AntCompatUncheckedException(String message, Throwable cause) {
        super(message, cause);
    }

}
