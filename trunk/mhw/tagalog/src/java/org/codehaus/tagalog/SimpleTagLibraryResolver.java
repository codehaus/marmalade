/*
 * $Id$
 */

package org.codehaus.tagalog;

import java.util.Map;

/**
 * A simple implementation of <code>TagLibraryResolver</code> that
 * provides a mapping from arbitrary URIs to registered
 * <code>TagLibrary</code>s.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public final class SimpleTagLibraryResolver
    implements FallbackTagLibraryResolver
{
    private Map tagLibraries = new java.util.TreeMap();

    public void addTagLibrary(String uri, TagLibrary tagLibrary) {
        if (tagLibrary == null)
            throw new NullPointerException("tag library is null");
        tagLibraries.put(uri, tagLibrary);
    }

    public TagLibrary resolve(String uri) {
        return (TagLibrary) tagLibraries.get(uri);
    }
}
