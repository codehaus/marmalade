/*
 * $Id$
 */

package org.codehaus.typle;

import junit.framework.TestCase;

/**
 * Tests for {@link TypeAnnotation} functionality.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class TypeAnnotationTest extends TestCase {
    private Type type;

    protected void setUp() throws Exception {
        super.setUp();
        type = new AnnotatedType(Java.INT_TYPE);
    }

    public void testAddAnnotation1() {
        A a = new A();
        type.addAnnotation(a);
        assertEquals(a, type.getAnnotation(A.class));

        B b = new B();
        type.addAnnotation(b);
        assertEquals(a, type.getAnnotation(A.class));
        assertEquals(b, type.getAnnotation(B.class));

        C c = new C();
        type.addAnnotation(c);
        assertEquals(a, type.getAnnotation(A.class));
        assertEquals(b, type.getAnnotation(B.class));
        assertEquals(c, type.getAnnotation(C.class));

        assertEquals(type, type.getAnnotation(A.class).getOwningType());
        assertEquals(type, type.getAnnotation(B.class).getOwningType());
        assertEquals(type, type.getAnnotation(C.class).getOwningType());
    }

    public void testRemoveAnnotation1() {
        A a = new A();
        type.addAnnotation(a);
        assertEquals(a, type.getAnnotation(A.class));
        type.removeAnnotation(A.class);
        assertNull(type.getAnnotation(A.class));
    }

    // Simple hierarchy of annotations, arranged as follows:
    //
    //          TypeAnnotation
    //              /    \
    //             A      D
    //            /
    //           B
    //          /
    //         C

    private static class A
        extends AbstractTypeAnnotation
        implements TypeAnnotation
    {
    }

    private static class B
        extends A
        implements TypeAnnotation
    {
    }

    private static class C
        extends A
        implements TypeAnnotation
    {
    }

    private static class D
        extends AbstractTypeAnnotation
        implements TypeAnnotation
    {
    }
}
