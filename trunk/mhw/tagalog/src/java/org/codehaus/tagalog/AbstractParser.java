/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * AbstractParser
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public abstract class AbstractParser implements TagalogParser {
    private ParserConfiguration configuration;

    /**
     * Construct an <code>AbstractParser</code> that uses the specified
     * parser configuration.
     *
     * @param resolver The fallback tag library resolver.
     */
    protected AbstractParser(ParserConfiguration configuration) {
        this.configuration = configuration;
    }
}
