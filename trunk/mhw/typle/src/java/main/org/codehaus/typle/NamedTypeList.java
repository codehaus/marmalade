/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * Ordered, immutable, list of {@link NamedType} objects.
 * The <code>NamedType</code> objects must have unique names.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class NamedTypeList {
    private final NamedType[] list;

    public NamedTypeList() {
        this.list = NamedType.EMPTY_ARRAY;
    }

    /**
     * Construct a <code>NamedTypeList</code> from an array of
     * <code>NamedType</code>s. This is a private contructor used internally
     * to create new instances; it does not verify that the object
     * constructed is internally valid, nor does it make a defensive copy of
     * the array.
     *
     * @param namedTypes Array to base instance on.
     */
    private NamedTypeList(NamedType[] namedTypes) {
        this.list = namedTypes;
    }

    public static NamedTypeList fromArray(NamedType[] array) {
        NamedTypeList newList = new NamedTypeList();

        for (int i = 0; i < array.length; i++) {
            newList = newList.add(array[i]);
        }
        return newList;
    }

    public NamedType[] toArray() {
        return NamedType.clone(list);
    }

    public NamedTypeList add(NamedType namedType) {
        String name = namedType.getName();
        NamedType[] newList = new NamedType[list.length + 1];

        for (int i = 0; i < list.length; i++) {
            if (list[i].getName().equals(name)) {
                throw new IllegalArgumentException(
                    "duplicate named type '" + name + "' in ["
                    + signature() + "]"
                );
            }
            newList[i] = list[i];
        }
        newList[list.length] = namedType;
        return new NamedTypeList(newList);
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof NamedTypeList))
            return false;
        NamedTypeList other = (NamedTypeList) o;
        if (list.length != other.list.length)
            return false;
        for (int i = 0; i < list.length; i++) {
            if (!list[i].equals(other.list[i]))
                return false; 
        }
        return true;
    }

    public int hashcode() {
        throw new UnsupportedOperationException();
    }

    public int size() {
        return list.length;
    }

    public NamedType get(int index) {
        return list[index];
    }

    public NamedType get(String name) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].getName().equals(name)) {
                return list[i];
            }
        }
        return null;
    }

    public String signature() {
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < list.length; i++) {
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(list[i].getType().getName());
            buf.append(" ");
            buf.append(list[i].getName());
        }
        return buf.toString();
    }

    public String toString() {
        return signature();
    }
}
