/*
 * $Id$
 */

package org.codehaus.tagalog;

import java.util.Map;

/**
 * <code>TagalogParser</code> defines the common interface for all Tagalog
 * parsers. The interface has two clients: it is used by Tagalog client code
 * that is parsing documents, and it is also available to {@link Tag}
 * implementations through the {@link Tag#setParser} method.
 * We refer to these two parts of the interface as the <i>client interface</i>
 * and the <i>tag interface</i>.
 * Each method provided by the interface is intended for use by either
 * the client or a tag, as indicated in the method
 * documentation.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public interface TagalogParser {
    /**
     * Perform the parse, using an empty {@link java.util.Map} to hold the
     * parse context. Part of the client interface.
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
     * parse context. Part of the client interface.
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
     * <p>
     * Part of the client interface.
     *
     * @return An array containing the parse errors, or <code>null</code>
     * if no errors were found.
     */
    ParseError[] parseErrors();

    /**
     * Return the location of the tag that is being parsed in the source
     * document. Part of the tag interface.
     *
     * @return Location of the current tag.
     */
    Location getLocation();
}
