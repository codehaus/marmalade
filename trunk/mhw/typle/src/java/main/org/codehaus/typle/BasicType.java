/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * A <code>BasicType</code> is a data type given to items that have no
 * further internal structure as far as the type system is concerned.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public final class BasicType extends TypeHelper implements Type {
    /**
     * Constructor for <code>BasicType</code>s.
     *
     * @param name Type's name.
     */
    public BasicType(String name) {
        super(name);
    }
}
