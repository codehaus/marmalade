/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * A <code>PrefixTagLibraryResolver</code> resolves URIs that consist of
 * a prefix and a suffix separated by a colon. An instance of
 * <code>PrefixTagLibraryResolver</code> handles a set of URIs with the
 * same prefix.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public interface PrefixTagLibraryResolver extends TagLibraryResolver {
    /**
     * Return the URI prefix for which the
     * <code>PrefixTagLibraryResolver</code> can create
     * <code>TagLibrary</code>s.
     *
     * @return A URI prefix.
     */
    String uriPrefix();
}
