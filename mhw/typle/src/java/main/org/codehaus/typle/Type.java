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
     */
    void resolvePlaceHolders() throws TypeLookupException;

    /**
     * Add annotation to this type. If an annotation of the same type
     * already exists then it is replaced.
     *
     * @param annotation The annotation to be added.
     */
    void addAnnotation(TypeAnnotation annotation);

    /**
     * Get the annotation of the specified class, if it exists.
     *
     * @param annotationClass Class of the annotation to search for.
     * @return The annotation, or <code>null</code> if no annotation
     * of the specified type could be found.
     */
    TypeAnnotation getAnnotation(Class annotationClass);

    /**
     * Remove any annotation of the specified class.
     *
     * @param annotationClass
     */
    void removeAnnotation(Class annotationClass);
}
