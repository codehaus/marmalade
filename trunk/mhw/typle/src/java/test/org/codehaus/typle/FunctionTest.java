/*
 * $Id$
 */

package org.codehaus.typle;

import junit.framework.TestCase;

/**
 * Tests for {@link Function}.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public class FunctionTest extends TestCase {
    private BasicType fooType;
    private BasicType barType;
    private BasicType bazType;
    private Binding foo;
    private Binding bar;

    protected void setUp() throws Exception {
        super.setUp();
        fooType = new BasicType("Foo");
        barType = new BasicType("Bar");
        bazType = new BasicType("Baz");
        foo = new Binding("foo", fooType);
        bar = new Binding("bar", barType);
    }

    /**
     * Test the names given to the primitives.
     */
    public void testEmpty() {
        Function f;

        f = new Function(bazType);
        assertEquals("(): Baz", f.getSignature());
        f = f.addFormalParameter("foo", fooType);
        assertEquals("(Foo foo): Baz", f.getSignature());
        f = f.addFormalParameter("bar", barType);
        assertEquals("(Foo foo, Bar bar): Baz", f.getSignature());
        f = new Function(bazType, new Binding[] { foo, bar });
        assertEquals("(Foo foo, Bar bar): Baz", f.getSignature());
    }
}
