/*
 * $Id$
 */

package org.codehaus.typle;

import junit.framework.TestCase;

/**
 * Tests for Java data type representations.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public class JavaTypesTest extends TestCase {

    /**
     * Test the names given to the primitives.
     */
    public void testPrimitiveNames() {
        assertEquals("boolean", Java.BOOLEAN_TYPE.getName());
        assertEquals("byte", Java.BYTE_TYPE.getName());
        assertEquals("char", Java.CHAR_TYPE.getName());
        assertEquals("short", Java.SHORT_TYPE.getName());
        assertEquals("int", Java.INT_TYPE.getName());
        assertEquals("long", Java.LONG_TYPE.getName());
        assertEquals("float", Java.FLOAT_TYPE.getName());
        assertEquals("double", Java.DOUBLE_TYPE.getName());
        assertEquals("void", Java.VOID_TYPE.getName());
    }

    /**
     * Test that creation of a primitive type must use a primitive class.
     */
    public void testIllegalPrimitiveType() {
        try {
            Type t = new JavaPrimitive(System.class);
            fail("created primitive type with non-primitive class");
        } catch (IllegalArgumentException e) {
            // ignore
        }
    }
}
