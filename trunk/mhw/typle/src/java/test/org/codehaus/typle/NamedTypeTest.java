/*
 * $Id$
 */

package org.codehaus.typle;

import junit.framework.TestCase;

/**
 * Tests for named data types.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public class NamedTypeTest extends TestCase {
    private BasicType fooType;
    private BasicType barType;
    private NamedType foo;
    private NamedType bar;

    protected void setUp() throws Exception {
        super.setUp();
        fooType = new BasicType("Foo");
        barType = new BasicType("Bar");
        foo = new NamedType("foo", fooType);
        bar = new NamedType("bar", barType);
    }

    /**
     * Test the names given to the primitives.
     */
    public void testAccessors() {
        assertEquals("foo", foo.getName());
        assertEquals(fooType, foo.getType());
        assertEquals("bar", bar.getName());
        assertEquals(barType, bar.getType());
    }

    /**
     * Test array cloning.
     */
    public void testArrayClone() {
        NamedType[] src;
        NamedType[] dest;

        src = new NamedType[] { foo, bar };
        dest = NamedType.clone(src);
        assertEquals(2, dest.length);
        for (int i = 0; i < dest.length; i++) {
            assertEquals(src[i], dest[i]);
        }
        dest = NamedType.clone(src, 4);
        assertEquals(4, dest.length);
        for (int i = 0; i < dest.length; i++) {
            if (i > 1) {
                assertNull(dest[i]);
            } else {
                assertEquals(src[i], dest[i]);
            }
        }
    }
}
