/*
 * $Id$
 */

package org.codehaus.typle;

import java.util.Map;

/**
 * <code>TypeFactory</code> for the <code>java</code> namespace.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
final class JavaTypeFactory implements TypeFactory {

    private static Map typeMap = new java.util.HashMap();

    static {
        addType(Java.BOOLEAN_TYPE.getTypeName(), Java.BOOLEAN_TYPE);
        addType(Java.BYTE_TYPE.getTypeName(), Java.BYTE_TYPE);
        addType(Java.CHAR_TYPE.getTypeName(), Java.CHAR_TYPE);
        addType(Java.SHORT_TYPE.getTypeName(), Java.SHORT_TYPE);
        addType(Java.INT_TYPE.getTypeName(), Java.INT_TYPE);
        addType(Java.LONG_TYPE.getTypeName(), Java.LONG_TYPE);
        addType(Java.FLOAT_TYPE.getTypeName(), Java.FLOAT_TYPE);
        addType(Java.DOUBLE_TYPE.getTypeName(), Java.DOUBLE_TYPE);
        addType(Java.VOID_TYPE.getTypeName(), Java.VOID_TYPE);
    }

    private static void addType(String name, Type type) {
        if (name == null)
            throw new NullPointerException("name is null");
        if (type == null)
            throw new NullPointerException("type is null");
        typeMap.put(name, type);
    }

    public Type lookup(String name) {
        Object o;
        Type t = null;

        if (name == null)
            throw new NullPointerException("name is null");
        if ((o = typeMap.get(name)) != null)
            return (Type) o;
        if (name.indexOf('.') != -1) {
            t = new JavaReferenceType(name);
        } else {
            try {
                o = Class.forName(name);
                t = new JavaReferenceType(name);
            } catch (ClassNotFoundException e) {
                try {
                    String qualifiedName = "java.lang." + name;
                    o = Class.forName(qualifiedName);
                    t = new JavaReferenceType(qualifiedName);
                } catch (ClassNotFoundException e2) {
                    // ignore
                }
            }
        }
        if (t != null)
            addType(name, t);
        return t;
    }
}
