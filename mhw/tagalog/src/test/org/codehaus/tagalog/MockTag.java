/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * Mock tag implementation for testing.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public final class MockTag extends AbstractTag {
    private boolean recycled = false;

    public boolean isRecycled() {
        return recycled;
    }

    public boolean recycle() {
        recycled = true;
        return true;
    }
}
