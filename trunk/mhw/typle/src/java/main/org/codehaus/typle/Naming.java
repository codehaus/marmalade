/*
 * $Id$
 */

package org.codehaus.typle;

import java.util.Map;

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

    private static Map namespaceFactories = new java.util.HashMap();

    public static void addNamespaceFactory(String namespace,
        TypeFactory factory)
    {
        if (namespace == null)
            throw new NullPointerException("namespace is null");
        if (factory == null)
            throw new NullPointerException("factory is null");
        namespaceFactories.put(namespace, factory);
    }

    static {
        addNamespaceFactory("java", new JavaTypeFactory());
    }

    /**
     * Return a type given a compound name. The compound name consists
     * of the namespace and local part separated by ':'.
     *
     * @param name Compound type name.
     * @return Type with the given name, or null if no such type could be
     *         found.
     * @throws TypeLookupException If the lookup process failed for some
     * reason, other than the requested type not existing.
     */
    public static Type lookup(String name)
        throws TypeLookupException
    {
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
     * @return Type with the given name, or null if no such type could be
     *         found.
     * @throws TypeLookupException If the lookup process failed for some
     * reason, other than the requested type not existing. 
     */
    public static Type lookup(String namespace, String localPart)
        throws TypeLookupException
    {
        if (namespace == null)
            throw new NullPointerException("namespace is null");
        if (localPart == null)
            throw new NullPointerException("local part is null");
        Object o = namespaceFactories.get(namespace);
        if (o == null)
            throw new IllegalArgumentException("namespace " + namespace
                                               + "not found");
        return ((TypeFactory) o).lookup(localPart);
    }
}
