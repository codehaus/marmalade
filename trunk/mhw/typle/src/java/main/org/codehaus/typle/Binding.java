/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * A <code>Binding</code> is an association of a name with a type.
 * Bindings are typically grouped together within some namespace.
 * For example, a record can be thought of as a set of bindings
 * between field names and types, while a function's arguments can
 * be considered as a list of bindings from formal parameter names
 * to types.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class Binding {
    private final String name;
    private Type type;

    /**
     * Empty array of <code>Binding</code>s.
     */
    public static final Binding[] EMPTY_ARRAY = new Binding[0];

    /**
     * Create an object representing a binding between a name and a data type.
     *
     * @param name The name of the binding. Cannot be <code>null</code>.
     * @param type The type that the name is bound to. Cannot be
     * <code>null</code>.
     * @throws NullPointerException if either argument is <code>null</code>.
     */
    public Binding(String name, Type type) {
        if (name == null) {
            if (type == null)
                throw new NullPointerException("name and type are null");
            throw new NullPointerException("name is null for type " + type);
        }
        if (type == null)
            throw new NullPointerException("type is null for name " + name);
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

    public void resolvePlaceHolders() throws TypeLookupException {
        if (type instanceof TypePlaceHolder) {
            type = ((TypePlaceHolder) type).resolve();
        }
    }

    /**
     * Duplicate an array of <code>Binding</code>s. The array is
     * duplicated, but the <code>Binding</code> objects that it contains
     * are shared between the two arrays.
     *
     * @param bindings Array of <code>Binding</code> objects to be cloned.
     * @return New array of <code>Binding</code>s.
     */
    public static Binding[] clone(Binding[] bindings) {
        return clone(bindings, bindings.length);
    }

    /**
     * Duplicate an array of <code>Binding</code>s into a new array of a
     * specified length. The size of the new array must be greater than or
     * equal to the size of the array passed as an argument. The array is
     * duplicated, but the <code>Binding</code> objects that it contains
     * are shared between the two arrays.
     *
     * @param bindings Array of <code>Binding</code> objects to be cloned.
     * @param newLength The size of the array that should be returned. The
     * new array must be large enough to contain all the entries in the
     * <code>namedTypes</code> array. If the new array is larger the unused
     * locations will be initialised to <code>null</code>.
     * @return New array of <code>Binding</code>s.
     */
    public static Binding[] clone(Binding[] bindings, int newLength) {
        if (newLength < bindings.length) {
            throw new IllegalArgumentException(
                "length of cloned array would be less than existing array"
            );
        }
        Binding[] r = new Binding[newLength];
        System.arraycopy(bindings, 0, r, 0, bindings.length);
        return r;
    }
}
