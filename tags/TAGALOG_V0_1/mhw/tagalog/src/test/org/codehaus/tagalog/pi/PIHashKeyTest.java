/*
 * $Id$
 */

package org.codehaus.tagalog.pi;

import java.util.Map;

import junit.framework.TestCase;

/**
 * Simple tests for the processing instruction {@link PIHashKey}.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class PIHashKeyTest extends TestCase {
    private PIHashKey key;

    protected void setUp() throws Exception {
        super.setUp();
        key = new PIHashKey("fred");
    }

    public void testConstructor() {
        try {
            key = new PIHashKey(null);
            fail("should have thrown NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        key = new PIHashKey("");
    }

    public void testGetTarget() {
        key = new PIHashKey("");
        assertEquals("", key.getTarget());

        key = new PIHashKey("fred");
        assertEquals("fred", key.getTarget());
    }

    public void testHashCode() {
        assertEquals("fred".hashCode(), key.hashCode());
    }

    public void testEqualsObject() {
        assertFalse(key.equals("fred"));
        assertTrue(key.equals(new PIHashKey("fred")));
    }

    /**
     * Verify that <code>PIHashKey</code> provides a set of keys distinct
     * from plain <code>String</code>s.
     */
    public void testHashMapBehaviour() {
        Map map = new java.util.HashMap();

        map.put(key, "fred-pi-data");
        map.put("fred", "normal fred data");
        map.put("mark", "mhw");
        map.put(new PIHashKey("mark"), "mark's pi");

        assertEquals("normal fred data", map.get("fred"));
        assertEquals("mhw", map.get("mark"));

        key = new PIHashKey("fred");
        assertEquals("fred-pi-data", map.get(key));

        key = new PIHashKey("mark");
        assertEquals("mark's pi", map.get(key));
    }
}
