/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * A single instance of <code>FallbackTagLibraryResolver</code> is used
 * by the parser to resolve URIs that have not been resolved by any of
 * the <code>PrefixTagLibraryResolver</code>s.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public interface FallbackTagLibraryResolver extends TagLibraryResolver {
    /**
     * Associate a tag library with a URI, so that
     * {@link TagLibraryResolver#resolve} will resolve the URI to the
     * supplied tag library.
     *
     * @param uri URI of the tag library.
     * @param tagLibrary The tag library itself.
     */
    void addTagLibrary(String uri, TagLibrary tagLibrary);
}
