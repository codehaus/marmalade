/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * Constants values of type {@link JavaPrimitive} that represent the Java
 * primitive types.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public class Java {
    // prevent instantiation
    private Java() {
    }

    public static final JavaPrimitive BOOLEAN_TYPE
                                            = new JavaPrimitive(Boolean.TYPE);
    public static final JavaPrimitive BYTE_TYPE
                                            = new JavaPrimitive(Byte.TYPE);
    public static final JavaPrimitive CHAR_TYPE
                                            = new JavaPrimitive(Character.TYPE);
    public static final JavaPrimitive SHORT_TYPE
                                            = new JavaPrimitive(Short.TYPE);
    public static final JavaPrimitive INT_TYPE
                                            = new JavaPrimitive(Integer.TYPE);
    public static final JavaPrimitive LONG_TYPE
                                            = new JavaPrimitive(Long.TYPE);
    public static final JavaPrimitive FLOAT_TYPE
                                            = new JavaPrimitive(Float.TYPE);
    public static final JavaPrimitive DOUBLE_TYPE
                                            = new JavaPrimitive(Double.TYPE);
    public static final JavaPrimitive VOID_TYPE
                                            = new JavaPrimitive(Void.TYPE);
}
