/*
 * $Id$
 */

package org.codehaus.typle;

import java.util.Map;

/**
 * Wrap an immutable implementation of the {@link Type} interface with
 * the ability to attach attributes.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class AnnotatedType implements Type {
    private Type baseType;

    private Map annotations;

    public AnnotatedType(Type baseType) {
        this.baseType = baseType;
    }

    public String getTypeName() {
        return baseType.getTypeName();
    }

    public Type getAnnotatedType() {
        return baseType;
    }

    /**
     * Change the type that the annotations are attached to. This
     * package-private method is used by {@link AbstractTypeVisitor} to
     * replace one annotated type with another. This saves us from having
     * to worry about cloning annotations.
     *
     * @param baseType
     */
    void setAnnotatedType(Type baseType) {
        this.baseType = baseType;
    }

    public String toString() {
        return baseType.toString();
    }

    public void resolvePlaceHolders() throws TypeLookupException {
        if (baseType instanceof TypePlaceHolder) {
            baseType = ((TypePlaceHolder) baseType).resolve();
        } else {
            baseType.resolvePlaceHolders();
        }
    }

    public synchronized void addAnnotation(TypeAnnotation annotation) {
        if (annotations == null)
            annotations = new java.util.HashMap();
        annotation.setOwningType(this);
        annotations.put(annotation.getClass(), annotation);
    }

    public synchronized TypeAnnotation getAnnotation(Class annotationClass) {
        if (annotations == null)
            return null;
        return (TypeAnnotation) annotations.get(annotationClass);
    }

    public synchronized void removeAnnotation(Class annotationClass) {
        if (annotations != null)
            annotations.remove(annotationClass);
    }
}
