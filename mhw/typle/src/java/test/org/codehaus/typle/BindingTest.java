/*
 * $Id$
 */

package org.codehaus.typle;

import junit.framework.TestCase;

/**
 * Tests for {@link Binding} objects.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public class BindingTest extends TestCase {
    private BasicType fooType;
    private BasicType barType;
    private Binding foo;
    private Binding bar;

    protected void setUp() throws Exception {
        super.setUp();
        fooType = new BasicType("Foo");
        barType = new BasicType("Bar");
        foo = new Binding("foo", fooType);
        bar = new Binding("bar", barType);
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
        Binding[] src;
        Binding[] dest;

        src = new Binding[] { foo, bar };
        dest = Binding.clone(src);
        assertEquals(2, dest.length);
        for (int i = 0; i < dest.length; i++) {
            assertEquals(src[i], dest[i]);
        }
        dest = Binding.clone(src, 4);
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
