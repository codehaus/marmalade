/*
 * $Id$
 */

package org.codehaus.typle;

import java.util.Iterator;
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

    private NamingContext context = null;

    protected void addSearchNamespace(String namespace) {
        if (context == null)
            context = new NamingContext(namespace);
        else
            context = context.addNamespace(namespace);
    }

    private Map typeMap = new java.util.HashMap();

    protected void addType(String name, Type type) {
        if (name == null)
            throw new NullPointerException("name is null");
        if (type == null)
            throw new NullPointerException("type is null");
        typeMap.put(name, type);
    }

    public Type lookup(String name) throws TypeLookupException {
        Type found = null;

        if (name == null)
            throw new NullPointerException("name is null");
        if (!fullyQualified(name) && context != null) {
            Iterator iter = context.iterator();
            while (iter.hasNext()) {
                String namespace = (String) iter.next();
                String qName = qualifiedName(namespace, name);
                found = lookupAndCheckAmbiguous(name, qName, found);
            }
        }
        return lookupAndCheckAmbiguous(name, name, found);
    }

    private Type lookupAndCheckAmbiguous(String name, String qName,
        Type previouslyFound)
        throws TypeLookupException
    {
        Type t = attemptLookup(qName);
        if (t != null) {
            if (previouslyFound != null)
                throw new TypeLookupException(name + " is ambiguous: "
                    + " could be " + previouslyFound
                    + " or " + t);
            return t;
        }
        return previouslyFound;
    }

    private Type attemptLookup(String name) throws TypeLookupException {
        Object o;
        String[] newTypes;

        o = typeMap.get(name);
        if (o == null) {
            newTypes = loadTypes(name);
            resolveTypes(newTypes);
            o = typeMap.get(name);
        }
        return (Type) o;
    }

    protected abstract boolean fullyQualified(String name);

    protected abstract String qualifiedName(String namespace, String name);

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

    private void resolveTypes(String[] types) throws TypeLookupException {
        for (int i = 0; i < types.length; i++) {
            ((Type) typeMap.get(types[i])).resolvePlaceHolders();
        }
    }
}
