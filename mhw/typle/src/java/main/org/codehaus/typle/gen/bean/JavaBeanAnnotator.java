/*
 * $Id$
 */

package org.codehaus.typle.gen.bean;

import org.codehaus.typle.AbstractTypeVisitor;
import org.codehaus.typle.AnnotatedType;
import org.codehaus.typle.RecordType;

/**
 * Traverse a type graph, adding {@link JavaBean} annotations to all the
 * {@link RecordType} nodes. The annotation instructs the
 * {@link JavaBeanGenerator} to generate Java classes named after the
 * record, with the {@link JavaBeanType} specified when the annotator is
 * constructed.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class JavaBeanAnnotator extends AbstractTypeVisitor {
    private final JavaBeanType beanType;

    /**
     * Create an Java Bean annotator that will generate classes of
     * the specified kind.
     *
     * @param beanType The kind of Java Bean that the annotation will
     * instruct {@link JavaBeanGenerator} to generate.
     */
    public JavaBeanAnnotator(JavaBeanType beanType) {
        this.beanType = beanType;
    }

    protected AnnotatedType visitAnnotatedType(AnnotatedType type) {
        if (type.getAnnotatedType() instanceof RecordType) {
            JavaBean bean = new JavaBean(type.getTypeName(), beanType);
            type.addAnnotation(bean);
        }
        return super.visitAnnotatedType(type);
    }
}
