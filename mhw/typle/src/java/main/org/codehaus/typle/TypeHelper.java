/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * Simple implementation of the {@link Type} contract, suitable for building
 * immutable classes that represent types. The class provides a simple
 * implementation that simply records the name given to the type at the
 * time of construction. The {@link TypeAnnotation} interfaces are not
 * supported because they necessitate mutable objects. Instead these
 * methods throw {@link UnsupportedOperationException} if they are
 * called.
 *
 * <p>
 * This class would probably be named <code>AbstractType</code> or
 * something similar, but for the misinterpretation that would likely result.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public abstract class TypeHelper extends UnannotatedType {
    private String name;

    /**
     * Create a type with the given name.
     *
     * @param name the name of the type.
     */
    protected TypeHelper(String name) {
        this.name = name;
    }

    /**
     * @see Type#getTypeName()
     */
    public String getTypeName() {
        return name;
    }

    public void resolvePlaceHolders() throws TypeLookupException {
    }

    /**
     * Convert the type into a meaningful String representation. This
     * implementation returns the same value as the {@link #getTypeName}
     * method.
     *
     * @return String representation of the type.
     */
    public String toString() {
        return getTypeName();
    }
}
