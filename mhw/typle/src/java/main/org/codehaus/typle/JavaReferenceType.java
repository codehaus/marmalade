/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * A <code>JavaReferenceType</code> is a data type given to items that are
 * reference types within the Java type system.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public final class JavaReferenceType extends TypeHelper implements Type {
    /**
     * Constructor for <code>JavaReferenceType</code>s.
     *
     * @param name Type's name.
     */
    public JavaReferenceType(String name) {
        super(name);
    }

    public String getUnqualifiedName() {
        String name = getName();
        return name.substring(name.lastIndexOf('.') + 1);
    }
}
