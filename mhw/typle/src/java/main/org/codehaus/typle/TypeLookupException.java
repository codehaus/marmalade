/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * Exception thrown to indicate that a type lookup operation failed for some
 * reason, other than the type not existing. This exception might be thrown
 * if information could not be extracted from some data source, for example.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class TypeLookupException extends Exception {
    /**
     * Create a type lookup exception.
     *
     * @param message Message explaining the reason for the exception.
     */
    public TypeLookupException(String message) {
        super(message);
    }

    /**
     * Create a type lookup exception.
     *
     * @param cause Exception that caused the type lookup failure.
     */
    public TypeLookupException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a type lookup exception.
     *
     * @param message Message explaining the reason for the exception.
     * @param cause Exception that caused the type lookup failure.
     */
    public TypeLookupException(String message, Throwable cause) {
        super(message, cause);
    }
}
