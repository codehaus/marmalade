/*
 * $Id$
 */

package org.codehaus.typle;

import junit.framework.TestCase;

/**
 * Tests for the {@link Naming} class.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class NamingTest extends TestCase {
    /*
     * Test for Type lookup(String)
     */
    public void testLookupString() {
        Type t;

        t = Naming.lookup("java:int");
        assertTrue(t instanceof JavaPrimitive);
        assertEquals("int", t.getTypeName());

        t = Naming.lookup("java:String");
        assertTrue(t instanceof JavaReferenceType);
        assertEquals("java.lang.String", t.getTypeName());

        t = Naming.lookup("java:org.codehaus.typle.Java");
        assertTrue(t instanceof JavaReferenceType);
        assertEquals("org.codehaus.typle.Java", t.getTypeName());

        t = Naming.lookup("java:nonexistent");
        assertNull(t);
    }

    /*
     * Test for Type lookup(String, String)
     */
    public void testLookupStringString() {
        Type t;

        t = Naming.lookup("java", "int");
        assertTrue(t instanceof JavaPrimitive);
        assertTrue(t.getTypeName().equals("int"));

        t = Naming.lookup("java", "String");
        assertTrue(t instanceof JavaReferenceType);
        assertEquals("java.lang.String", t.getTypeName());

        t = Naming.lookup("java", "org.codehaus.typle.Java");
        assertTrue(t instanceof JavaReferenceType);
        assertEquals("org.codehaus.typle.Java", t.getTypeName());

        t = Naming.lookup("java", "nonexistent");
        assertNull(t);
    }
}
