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

    /**
     * Return the type signature of the type, if such a concept makes sense.
     * For basic types this will typically be the same value as that returned
     * by {@link #getName}.
     *
     * @return the type signature.
     */
    String getSignature();
}
