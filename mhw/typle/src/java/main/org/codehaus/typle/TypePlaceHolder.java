/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * A <code>TypePlaceHolder</code> acts as a stand-in for a real data type
 * object at times when the real data type might not exist already. For
 * example, when reading multiple type definitions from a data source some
 * data types may reference other data types from the same source that have
 * not yet been created.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public final class TypePlaceHolder implements Type {
    private final String name;
    private transient Type type;

    /**
     * Constructor for <code>TypePlaceHolder</code>s.
     *
     * @param name Type's name.
     */
    public TypePlaceHolder(String name) {
        this.name = name;
    }

    /**
     * @see Type#getTypeName()
     */
    public String getTypeName() {
        if (type == null)
            return name;
        return type.getTypeName();
    }

    public Type getWrappedType() {
        return type;
    }

    public void resolvePlaceHolders() throws TypeLookupException {
        throw new TypeLookupException("attempt to resolve place-holders within "
            + this);
    }

    public String toString() {
        return "placeholder(" + name + ")";
    }
}
