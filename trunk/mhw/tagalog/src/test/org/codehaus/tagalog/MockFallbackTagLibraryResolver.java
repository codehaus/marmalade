/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * Mock implementation of {@link FallbackTagLibraryResolver}.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public final class MockFallbackTagLibraryResolver
    implements FallbackTagLibraryResolver
{
    boolean addTagLibraryCalled = false;

    boolean resolveCalled = false;

    public void addTagLibrary(String uri, TagLibrary tagLibrary) {
        addTagLibraryCalled = true;
    }

    public TagLibrary resolve(String uri) {
        resolveCalled = true;
        return null;
    }
}
