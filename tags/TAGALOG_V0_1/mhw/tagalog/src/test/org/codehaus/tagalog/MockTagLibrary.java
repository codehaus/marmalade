/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * Mock implementation of {@link TagLibrary}.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class MockTagLibrary extends AbstractTagLibrary {
    public MockTagLibrary() {
    }

    public MockTagLibrary(String tagName, Class tagClass) {
        registerTag(tagName, tagClass);
    }
}
