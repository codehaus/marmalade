/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * Ordered, immutable, list of {@link Binding} objects.
 * The <code>Binding</code> objects must have unique names.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class BindingList {
    private final Binding[] list;

    public BindingList() {
        this.list = Binding.EMPTY_ARRAY;
    }

    /**
     * Construct a <code>BindingList</code> from an array of
     * <code>Binding</code>s. This is a private contructor used internally
     * to create new instances; it does not verify that the object
     * constructed is internally valid, nor does it make a defensive copy of
     * the array.
     *
     * @param namedTypes Array to base instance on.
     */
    private BindingList(Binding[] namedTypes) {
        this.list = namedTypes;
    }

    public static BindingList fromArray(Binding[] array) {
        BindingList newList = new BindingList();

        for (int i = 0; i < array.length; i++) {
            newList = newList.add(array[i]);
        }
        return newList;
    }

    public Binding[] toArray() {
        return Binding.clone(list);
    }

    public BindingList add(Binding binding) {
        String name = binding.getName();
        Binding[] newList = new Binding[list.length + 1];

        for (int i = 0; i < list.length; i++) {
            if (list[i].getName().equals(name)) {
                throw new IllegalArgumentException(
                    "duplicate binding '" + name + "' in ["
                    + signature() + "]"
                );
            }
            newList[i] = list[i];
        }
        newList[list.length] = binding;
        return new BindingList(newList);
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BindingList))
            return false;
        BindingList other = (BindingList) o;
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

    public Binding get(int index) {
        return list[index];
    }

    public Binding get(String name) {
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
            buf.append(list[i].getType().getTypeName());
            buf.append(" ");
            buf.append(list[i].getName());
        }
        return buf.toString();
    }

    public String toString() {
        return signature();
    }
}
