/*
 * $Id$
 */

package org.codehaus.typle;

import junit.framework.TestCase;

/**
 * Tests for <code>RecordType</code>s.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public class RecordTypeTest extends TestCase {
    private BasicType foo;
    private BasicType bar;
    private RecordType type;

    protected void setUp() throws Exception {
        super.setUp();
        type = new RecordType();
        foo = new BasicType("Foo");
        bar = new BasicType("Bar");
    }

    public void testAddField() {
        type = type.addField("foo", foo);
        assertEquals(foo, type.getField("foo"));
        assertNull(type.getField("bar"));
        type = type.addField("bar", bar);
        assertEquals(foo, type.getField("foo"));
        assertEquals(bar, type.getField("bar"));
    }

    public void testAddDuplicateField() {
        type = type.addField("foo", foo);
        try {
            type.addField("foo", foo);
            fail("managed to add field 'foo' twice");
        } catch (IllegalArgumentException e) {
            // ignore
        }
    }

    public void testGetSignature() {
        assertEquals("{}", type.getSignature());
        type = type.addField("foo", foo);
        assertEquals("{Foo foo}", type.getSignature());
        type = type.addField("bar", bar);
        assertEquals("{Foo foo, Bar bar}", type.getSignature());
    }
}
