/*
 * $Id$
 */

package org.codehaus.typle;

import java.util.Map;

/**
 * Base implementation of the <code>TypeFactory</code> interface. This class
 * provides the following functionality to support implementing the
 * <code>TypeFactory</code> interface.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public abstract class AbstractTypeFactory {

    private static Map typeMap = new java.util.HashMap();

    protected static void addType(String name, Type type) {
        if (name == null)
            throw new NullPointerException("name is null");
        if (type == null)
            throw new NullPointerException("type is null");
        typeMap.put(name, type);
    }

    public Type lookup(String name) {
        Object o;
        Type t = null;

        if (name == null)
            throw new NullPointerException("name is null");
        if ((o = typeMap.get(name)) != null)
            return (Type) o;
        t = resolveType(name);
        if (t != null)
            addType(name, t);
        return t;
    }

    /**
     * Instantiate the <code>Type</code> object denoted by the given
     * type name.
     *
     * @param name The name of the type to resolve.
     * @return Instantiated <code>Type</code>.
     */
    protected abstract Type resolveType(String name);
}
