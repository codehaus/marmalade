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
    private RecordType namedType;

    protected void setUp() throws Exception {
        super.setUp();
        type = new RecordType();
        namedType = new RecordType("TheRecord");
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

        namedType = namedType.addField("foo", foo);
        assertEquals(foo, namedType.getField("foo"));
        assertNull(namedType.getField("bar"));
        namedType = namedType.addField("bar", bar);
        assertEquals(foo, namedType.getField("foo"));
        assertEquals(bar, namedType.getField("bar"));
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

    public void testFields() {
        BindingList fields;

        type = new RecordType();
        fields = type.getFields();
        assertEquals(0, fields.size());
        type = type.addField("foo", foo);
        fields = type.getFields();
        assertEquals(1, fields.size());
        assertEquals("foo", fields.get(0).getName());
        assertEquals("Foo", fields.get("foo").getType().getTypeName());
    }

    public void testGetSignature() {
        assertEquals("[]", type.getTypeName());
        type = type.addField("foo", foo);
        assertEquals("[Foo foo]", type.getTypeName());
        type = type.addField("bar", bar);
        assertEquals("[Foo foo, Bar bar]", type.getTypeName());
    }
}