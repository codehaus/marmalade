/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * Simple implementation of the {@link TypeAnnotation} methods in the
 * {@link Type} interface suitable for <code>Type</code> objects that
 * do not store annotations. The methods are defined to throw
 * {@link UnsupportedOperationException} if they are called.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public abstract class UnannotatedType {
    /**
     * Stub implementation of {@link Type#addAnnotation} that simply
     * throws {@link UnsupportedOperationException}.
     *
     * @param annotation The annotation to be added.
     * @throws UnsupportedOperationException
     */
    public void addAnnotation(TypeAnnotation annotation) {
        throw new UnsupportedOperationException();
    }

    /**
     * Stub implementation of {@link Type#getAnnotation} that simply
     * throws {@link UnsupportedOperationException}.
     *
     * @param annotationClass Class of the annotation to search for.
     * @return Nothing
     * @throws UnsupportedOperationException
     */
    public TypeAnnotation getAnnotation(Class annotationClass) {
        throw new UnsupportedOperationException();
    }

    /**
     * Stub implementation of {@link Type#removeAnnotation} that simply
     * throws {@link UnsupportedOperationException}.
     *
     * @param annotationClass
     * @throws UnsupportedOperationException
     */
    public void removeAnnotation(Class annotationClass) {
        throw new UnsupportedOperationException();
    }
}
