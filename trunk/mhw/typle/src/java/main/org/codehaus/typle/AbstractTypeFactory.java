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

    private Map typeMap = new java.util.HashMap();

    protected void addType(String name, Type type) {
        if (name == null)
            throw new NullPointerException("name is null");
        if (type == null)
            throw new NullPointerException("type is null");
        typeMap.put(name, type);
    }

    public Type lookup(String name) throws TypeLookupException {
        Object o;
        String[] newTypes;

        if (name == null)
            throw new NullPointerException("name is null");
        if ((o = typeMap.get(name)) != null)
            return (Type) o;
        newTypes = loadTypes(name);
        resolveTypes(newTypes);
        if ((o = typeMap.get(name)) != null)
            return (Type) o;
        return null;
    }

    /**
     * Instantiate the <code>Type</code> object denoted by the given
     * type name, placing it into the type map using the {@link #addType}
     * method. This method will be called when a type with the given name
     * does not exist in the type map.
     * When the method returns the type map must contain an entry for
     * the type if one exists. Other types can be loaded into the type
     * map at the same time.
     *
     * @param name The name of the type to load.
     */
    protected abstract String[] loadTypes(String name)
        throws TypeLookupException;

    private void resolveTypes(String[] types) {
        
    }
}
