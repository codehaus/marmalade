/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * Factory for {@link Type} objects.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public interface TypeFactory {
    /**
     * Lookup a type by name.
     *
     * @param name The name of the type to lookup. Must not be null.
     * @return The type with the name supplied name, or null if no such type
     * exists.
     * @throws TypeLookupException If the lookup process failed for some
     * reason, other than the requested type not existing.
     */
    Type lookup(String name) throws TypeLookupException;
}
