/*
 * $Id$
 */

package org.codehaus.typle.gen.bean;

import org.codehaus.typle.AbstractTypeVisitor;
import org.codehaus.typle.Binding;
import org.codehaus.typle.JavaPrimitive;
import org.codehaus.typle.JavaReferenceType;
import org.codehaus.typle.Type;
import org.codehaus.typle.src.SourceContainer;
import org.codehaus.typle.src.java.Field;

/**
 * Generate a {@link Field} source artefact for each
 * {@link JavaReferenceType} and {@link JavaPrimitive} object in the
 * type tree.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class FieldGenerator extends AbstractTypeVisitor {
    private final SourceContainer source;

    public FieldGenerator(SourceContainer source) {
        this.source = source;
    }

    protected Binding visitBinding(Binding binding) {
        Type t = binding.getType();
        if (t instanceof JavaReferenceType) {
            JavaReferenceType javaType = (JavaReferenceType) t;
            source.add(new Field(javaType.getUnqualifiedName(),
                                            binding.getName()));
        } else if (t instanceof JavaPrimitive) {
            source.add(new Field(binding.getType().getTypeName(),
                                            binding.getName()));
        }
        return super.visitBinding(binding);
    }
}
