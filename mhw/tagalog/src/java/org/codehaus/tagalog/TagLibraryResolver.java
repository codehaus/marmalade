/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * A <code>TagLibraryResolver</code> is responsible for mapping an XML
 * namespace URI into a tag library. Some <code>TagLibraryResolver</code>s
 * resolve URIs with a specific prefix; these resolvers implement the
 * {@link PrefixTagLibraryResolver} interface. An implementation of
 * {@link FallbackTagLibraryResolver} is used to resolve any URI which
 * is not resolved by the <code>PrefixTagLibraryResolver</code>s.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public interface TagLibraryResolver {
    /**
     * Resolve a URI to a <code>TagLibrary</code> instance. Implementations
     * of {@link PrefixTagLibraryResolver} will receive the URI with the
     * prefix removed, while other implementations of this interface will
     * receive the full URI.
     *
     * @param uri The URI to resolve.
     * @return The tag library that handles tags in the namespace identified
     * by the URI, or <code>null</code> if no such tag library is known.
     */
    TagLibrary resolve(String uri);
}
