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
     * Return the local part of the type's name.
     *
     * @return the local part of the type's name.
     */
    String getName();

    /**
     * Return the type signature of the type, if such a concept makes sense.
     * For basic types this will typically be the same value as that returned
     * by {@link #getName}.
     *
     * @return the type signature.
     */
    String getSignature();
}
