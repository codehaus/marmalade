/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * MockPrefixTagLibraryResolver
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public final class MockPrefixTagLibraryResolver
    implements PrefixTagLibraryResolver
{
    private String prefix;

    private TagLibrary tagLibrary;

    public MockPrefixTagLibraryResolver(String prefix, TagLibrary tagLibrary) {
        this.prefix = prefix;
        this.tagLibrary = tagLibrary;
    }

    public String uriPrefix() {
        return prefix;
    }

    public TagLibrary resolve(String uri) {
        return tagLibrary;
    }
}
