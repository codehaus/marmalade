/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * A <code>TagException</code> can be thrown by a {@link Tag} instance to
 * indicate that a non-fatal error has occurred during parsing. The messages
 * from such errors are collected together and made available through the
 * {@link org.codehaus.tagalog.TagalogParser#parseErrors()} method after
 * the parse finishes. Any exception detail will be lost in the process,
 * although the message will be preserved.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class TagException extends Exception {
    /**
     * Construct a tag exception with the given message.
     *
     * @param message Message describing the exceptional condition.
     */
    public TagException(String message) {
        super(message);
    }

    /**
     * Construct a tag exception with the given message, attaching the
     * exception that caused the condition.
     *
     * @param message Message describing the exceptional condition.
     * @param cause Exception that caused the condition.
     */
    public TagException(String message, Throwable cause) {
        super(message, cause);
    }
}
