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
        assertEquals("boolean", Java.BOOLEAN_TYPE.getTypeName());
        assertEquals("byte", Java.BYTE_TYPE.getTypeName());
        assertEquals("char", Java.CHAR_TYPE.getTypeName());
        assertEquals("short", Java.SHORT_TYPE.getTypeName());
        assertEquals("int", Java.INT_TYPE.getTypeName());
        assertEquals("long", Java.LONG_TYPE.getTypeName());
        assertEquals("float", Java.FLOAT_TYPE.getTypeName());
        assertEquals("double", Java.DOUBLE_TYPE.getTypeName());
        assertEquals("void", Java.VOID_TYPE.getTypeName());
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
