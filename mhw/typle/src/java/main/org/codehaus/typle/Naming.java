/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * Simple interface to the typle naming service.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class Naming {
    // prevent instantiation
    private Naming() {
    }

    /**
     * Return a type given a compound name. The compound name consists
     * of the namespace and local part separated by ':'.
     *
     * @param name Compound type name.
     * @return Type with the given name.
     */
    public Type lookup(String name) {
        int colon = name.indexOf(':');
        if (colon == -1)
            throw new IllegalArgumentException("compound type name "
                                               + name + " is malformed");
        return lookup(name.substring(0, colon),
                      name.substring(colon + 1));
    }

    /**
     * Return a type from a namespace.
     *
     * @param namespace The namespace that the type is in.
     * @param localPart The name of the type within the namespace.
     * @return Type with the given name.
     */
    public Type lookup(String namespace, String localPart) {
        if (namespace == null)
            throw new NullPointerException("namespace is null");
        if (localPart == null)
            throw new NullPointerException("local part is null");
        return null;
    }
}
