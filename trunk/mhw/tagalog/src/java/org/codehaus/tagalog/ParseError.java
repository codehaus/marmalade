/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * A <code>ParseError</code> collects information about some non-fatal error
 * encountered while parsing content. Typically these errors are raised by
 * implementations of {@link Tag} as they validate and process the content.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public final class ParseError {
    public static final ParseError[] EMPTY_ARRAY = new ParseError[0];

    private final String message;

    private final Location location;

    public ParseError(String message, Location location) {
        this.message = message;
        this.location = location;
    }

    /**
     * Returns the error message.
     *
     * @return The error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the location where the error occurred.
     *
     * @return the location where the error occurred.
     */
    public Location getLocation() {
        return location;
    }

    public String toString() {
        return location + ": " + message;
    }
}
