/*
 * $Id$
 */

package org.codehaus.typle.gen.bean;

import org.codehaus.typle.AbstractTypeVisitor;
import org.codehaus.typle.Binding;
import org.codehaus.typle.JavaReferenceType;
import org.codehaus.typle.Type;
import org.codehaus.typle.src.SourceContainer;
import org.codehaus.typle.src.java.Import;

/**
 * Generate an {@link Import} source artefact for every
 * {@link JavaReferenceType} object in the type tree.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class ImportGenerator extends AbstractTypeVisitor {
    private final SourceContainer source;

    public ImportGenerator(SourceContainer source) {
        this.source = source;
    }

    protected Binding visitBinding(Binding binding) {
        Type t = binding.getType();
        if (t instanceof JavaReferenceType) {
            source.add(new Import(t.getTypeName()));
        }
        return super.visitBinding(binding);
    }
}
