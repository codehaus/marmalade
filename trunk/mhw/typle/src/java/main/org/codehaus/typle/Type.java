/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * Common interface for all objects that represent data types.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public interface Type {
    /**
     * Return the type's name. If the type has a meaningful name in some
     * type system then that should be returned; in this case the name
     * returned should be a valid local part that could be passed to the
     * {@link Naming#lookup} method.
     *
     * @return the local part of the type's name.
     */
    String getTypeName();
}
