/*
 * $Id$
 */

package org.codehaus.typle;

import junit.framework.TestCase;

/**
 * Tests for lists of named data types.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public class NamedTypeListTest extends TestCase {
    private BasicType fooType;
    private BasicType barType;
    private BasicType bazType;
    private NamedType foo;
    private NamedType bar;
    private NamedType baz;

    protected void setUp() throws Exception {
        super.setUp();
        fooType = new BasicType("Foo");
        barType = new BasicType("Bar");
        bazType = new BasicType("Baz");
        foo = new NamedType("foo", fooType);
        bar = new NamedType("bar", barType);
        baz = new NamedType("baz", bazType);
    }

    /**
     * Test the names given to the primitives.
     */
    public void testEmpty() {
        NamedTypeList list;

        list = new NamedTypeList();
        assertEquals(0, list.size());
        assertEquals(list, new NamedTypeList());
    }

    /**
     * Test addition of elements to list.
     */
    public void testAdd() {
        NamedTypeList list;

        list = new NamedTypeList();
        assertEquals(0, list.size());
        list = list.add(foo);
        assertEquals(1, list.size());
        list = list.add(bar);
        assertEquals(2, list.size());
        list = list.add(baz);
        assertEquals(3, list.size());
        assertEquals(foo, list.get(0));
        assertEquals(bar, list.get(1));
        assertEquals(baz, list.get(2));
        assertEquals(foo, list.get(foo.getName()));
        assertEquals(bar, list.get(bar.getName()));
        assertEquals(baz, list.get(baz.getName()));
        try {
            list.add(foo);
            fail();
        } catch (IllegalArgumentException e1) {
            try {
                list.add(bar);
                fail();
            } catch (IllegalArgumentException e2) {
                try {
                    list.add(baz);
                    fail();
                } catch (IllegalArgumentException e3) {
                    // ignore
                }
            }
        }
    }

    public void testEquals() {
        NamedTypeList list1, list2;

        list1 = new NamedTypeList();
        list2 = new NamedTypeList();
        assertEquals(list1, list2);
        list1 = list1.add(foo);
        list2 = list2.add(foo);
        assertEquals(list1, list2);
        list1 = list1.add(bar);
        list2 = list2.add(bar);
        assertEquals(list1, list2);
        list1 = list1.add(baz);
        list2 = list2.add(baz);
        assertEquals(list1, list2);
        assertFalse(list1.equals(new Object()));
        list2 = new NamedTypeList();
        assertFalse(list1.equals(list2));
        list2 = list2.add(baz);
        assertFalse(list1.equals(list2));
        list2 = list2.add(foo);
        assertFalse(list1.equals(list2));
        list2 = list2.add(bar);
        assertFalse(list1.equals(list2));
        assertEquals(list1.size(), list2.size());
    }
}
