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
        Function f;

        f = new Function(bazType);
        assertEquals("(): Baz", f.getSignature());
        f = f.addFormalParameter(foo);
        assertEquals("(Foo foo): Baz", f.getSignature());
        f = new Function(bazType, new NamedType[] { foo, bar });
        assertEquals("(Foo foo, Bar bar): Baz", f.getSignature());
    }
}
