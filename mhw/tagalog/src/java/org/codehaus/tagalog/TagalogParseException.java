/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * TagalogParseException
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class TagalogParseException extends Exception {
    public TagalogParseException() {
        super();
    }

    public TagalogParseException(String message) {
        super(message);
    }

    public TagalogParseException(Throwable cause) {
        super(cause);
    }

    public TagalogParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
