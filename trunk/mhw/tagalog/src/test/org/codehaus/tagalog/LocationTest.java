/*
 * $Id$
 */

package org.codehaus.tagalog;

import junit.framework.TestCase;

/**
 * Tests for the {@link Location} class.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class LocationTest extends TestCase {
    private Location all;

    private Location withoutName;

    private Location withoutColumn;

    private Location withoutNameColumn;

    private Location withoutLineColumn;

    private Location withoutNameLineColumn;

    protected void setUp() throws Exception {
        super.setUp();
        all = new Location("filename", 25, 44);
        withoutName = new Location(null, 66, 32);
        withoutColumn = new Location("filename", 25, -1);
        withoutNameColumn = new Location(null, 66, -1);
        withoutLineColumn = new Location("filename", -1, -1);
        withoutNameLineColumn = new Location(null, -1, -1);
    }

    public void testConstructorExceptions() {
        try {
            Location l = new Location("fred", -1, 44);
            fail(l.toString());
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public void testGetFilename() {
        assertEquals("filename", all.getFilename());
        assertEquals("filename", withoutColumn.getFilename());
        assertEquals("filename", withoutLineColumn.getFilename());
        assertEquals(null, withoutName.getFilename());
        assertEquals(null, withoutNameColumn.getFilename());
        assertEquals(null, withoutNameLineColumn.getFilename());
    }

    public void testGetLine() {
        assertEquals(25, all.getLine());
        assertEquals(66, withoutName.getLine());
        assertEquals(-1, withoutLineColumn.getLine());
    }

    public void testGetColumn() {
        assertEquals(44, all.getColumn());
        assertEquals(32, withoutName.getColumn());
        assertEquals(-1, withoutColumn.getColumn());
    }

    public void testToString() {
        assertEquals("filename:25:44", all.toString());
        assertEquals("filename:25", withoutColumn.toString());
        assertEquals("filename", withoutLineColumn.toString());
        assertEquals("66:32", withoutName.toString());
        assertEquals("66", withoutNameColumn.toString());
        assertEquals("", withoutNameLineColumn.toString());
    }
}
