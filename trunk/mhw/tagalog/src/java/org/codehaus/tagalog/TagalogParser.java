/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * <code>TagalogParser</code> defines the common interface for all Tagalog
 * parsers. This amounts to a single method to trigger the actual parse.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public interface TagalogParser {
    void parse() throws Exception;
}
