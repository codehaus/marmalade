/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * A type annotation is a piece of information attached to a data type.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public interface TypeAnnotation {
    /**
     * Set the type that this annotation is attached to. This method can
     * only be called once. Subsequent calls will throw an
     * {@link IllegalStateException}.
     *
     * @param owningType The annotated type that this annotation is being
     * attached to.
     * @throws IllegalStateException if this method has already been called
     * for this object.
     */
    void setOwningType(AnnotatedType owningType);

    /**
     * Get the annotated type node that this annotation is attached to.
     *
     * @return Annotated type.
     * @throws IllegalStateException if the {@link #setOwningType} method
     * has not been called already.
     */
    AnnotatedType getOwningType();
}
