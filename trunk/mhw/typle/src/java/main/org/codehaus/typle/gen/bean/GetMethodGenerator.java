/*
 * $Id$
 */

package org.codehaus.typle.gen.bean;

import org.codehaus.typle.AbstractTypeVisitor;
import org.codehaus.typle.Binding;
import org.codehaus.typle.src.SourceContainer;
import org.codehaus.typle.src.java.JavaHelper;

/**
 * Generate a "get" {@link Method} source artefact for every
 * {@link JavaReferenceType} object in the type tree.
 * The generated method has a body that returns the value of the
 * associated field.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class GetMethodGenerator extends AbstractTypeVisitor {
    private final SourceContainer source;

    public GetMethodGenerator(SourceContainer source) {
        this.source = source;
    }

    protected Binding visitBinding(Binding binding) {
        JavaHelper.addGetter(source, binding);
        return super.visitBinding(binding);
    }
}
