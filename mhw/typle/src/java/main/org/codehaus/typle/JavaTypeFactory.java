/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * <code>TypeFactory</code> for the <code>java</code> namespace.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
final class JavaTypeFactory
    extends AbstractTypeFactory
    implements TypeFactory
{
    {
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

    protected String[] loadTypes(String name) {
        Type t = null;

        if (name.indexOf('.') != -1) {
            t = new JavaReferenceType(name);
        } else {
            try {
                Class.forName(name);
                t = new JavaReferenceType(name);
            } catch (ClassNotFoundException e) {
                try {
                    name = "java.lang." + name;
                    Class.forName(name);
                    t = new JavaReferenceType(name);
                } catch (ClassNotFoundException e2) {
                    // ignore
                }
            }
        }
        if (t != null) {
            addType(name, t);
            return new String[] { name };
        } else {
            return new String[0];
        }
    }
}
