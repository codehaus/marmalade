/*
 * $Id$
 */

package org.codehaus.tagalog;

import java.util.Map;

/**
 * <code>TagalogParser</code> defines the common interface for all Tagalog
 * parsers. This amounts to a couple of methods to trigger the actual parse.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public interface TagalogParser {
    /**
     * Perform the parse, using an empty {@link java.util.Map} to hold the
     * parse context.
     *
     * @return The object returned by the {@link Tag#end} method of the
     * root element.
     *
     * @throws TagalogParseException if there was a serious problem that
     * prevented the content from being parsed.
     */
    Object parse() throws TagalogParseException;

    /**
     * Perform the parse, using the supplied {@link java.util.Map} to hold the
     * parse context.
     *
     * @param context A map used to hold shared information during the
     * parse.
     *
     * @return The object returned by the {@link Tag#end} method of the
     * root element.
     *
     * @throws TagalogParseException if there was a serious problem that
     * prevented the content from being parsed.
     */
    Object parse(Map context) throws TagalogParseException;

    /**
     * Return the parse errors that were collected during the parse. These
     * are the errors that are raised by tags during the normal parsing of
     * the content. They are tagged with the location in the source file
     * where the error occurred.
     *
     * @return An array containing the parse errors, or <code>null</code>
     * if no errors were found.
     */
    ParseError[] parseErrors();
}
