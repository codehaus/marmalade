/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * Simple implementation of the basic contract specified by the
 * {@link TypeAnnotation} interface.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public abstract class AbstractTypeAnnotation {
    private AnnotatedType owningType;

    public void setOwningType(AnnotatedType owningType) {
        if (this.owningType != null) {
            throw new IllegalStateException("owning type already set to "
                + this.owningType + " when trying to set it to "
                + owningType);
        }
        this.owningType = owningType;
    }

    public AnnotatedType getOwningType() {
        if (this.owningType == null)
            throw new IllegalStateException("owning type not set");
        return owningType;
    }
}
