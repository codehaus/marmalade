/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * Simple implementation of the {@link Type} contract, suitable for building
 * non-mutable classes that represent types. This class would probably be
 * named <code>AbstractType</code> or something similar, but for the
 * misinterpretation that would likely result.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public abstract class TypeHelper {
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
     * @see Type#getName()
     */
    public String getName() {
        return name;
    }

    /**
     * @see Type#getSignature
     */
    public String getSignature() {
        return name;
    }
}
