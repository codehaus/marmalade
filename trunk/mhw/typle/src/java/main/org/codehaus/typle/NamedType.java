/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * Binding of a name to a type.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class NamedType {
    private String name;
    private Type type;

    /**
     * Empty array of <code>NamedType</code>s.
     */
    public static final NamedType[] EMPTY_ARRAY = new NamedType[0];

    /**
     * Create an object representing a binding between a name and a data type.
     *
     * @param name
     * @param type
     */
    public NamedType(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    /**
     * @return Binding name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Bound type.
     */
    public Type getType() {
        return type;
    }

    /**
     * Duplicate an array of <code>NamedType</code>s. The array is
     * duplicated, but the <code>NamedType</code> objects that it contains
     * are shared between the two arrays.
     *
     * @param namedTypes Array of <code>NamedType</code> objects to be cloned.
     * @return New array of <code>NamedType</code>s.
     */
    public static NamedType[] clone(NamedType[] namedTypes) {
        return clone(namedTypes, namedTypes.length);
    }

    /**
     * Duplicate an array of <code>NamedType</code>s into a new array of a
     * specified length. The size of the new array must be greater than or
     * equal to the size of the array passed as an argument. The array is
     * duplicated, but the <code>NamedType</code> objects that it contains
     * are shared between the two arrays.
     *
     * @param namedTypes Array of <code>NamedType</code> objects to be cloned.
     * @param newLength The size of the array that should be returned. The
     * new array must be large enough to contain all the entries in the
     * <code>namedTypes</code> array. If the new array is larger the unused
     * locations will be initialised to <code>null</code>.
     * @return New array of <code>NamedType</code>s.
     */
    public static NamedType[] clone(NamedType[] namedTypes, int newLength) {
        if (newLength < namedTypes.length) {
            throw new IllegalArgumentException(
                "length of cloned array would be less than existing array"
            );
        }
        NamedType[] r = new NamedType[newLength];
        System.arraycopy(namedTypes, 0, r, 0, namedTypes.length);
        return r;
    }
}
