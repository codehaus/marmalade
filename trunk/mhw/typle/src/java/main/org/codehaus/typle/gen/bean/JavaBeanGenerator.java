/*
 * $Id$
 */

package org.codehaus.typle.gen.bean;

import java.io.IOException;

import org.codehaus.typle.Type;
import org.codehaus.typle.src.SourceFile;
import org.codehaus.typle.src.SourceFileWriter;
import org.codehaus.typle.src.java.JavaClass;
import org.codehaus.typle.src.java.JavaHelper;
import org.codehaus.typle.src.java.JavaSource;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class JavaBeanGenerator {

    public void generate(Type type, SourceFileWriter writer)
        throws IOException
    {
        JavaBean annotation;
        SourceFile source;
        JavaClass srcClass;

        annotation = (JavaBean) type.getAnnotation(JavaBean.class);
        if (!annotation.generate())
            return;

        source = new SourceFile(JavaSource.COMPARATOR);
        srcClass = JavaHelper.initSourceFile(source, annotation.getBeanName());
        new ImportGenerator(source).visit(type);
        new FieldGenerator(srcClass).visit(type);
        new GetMethodGenerator(srcClass).visit(type);
        new SetMethodGenerator(srcClass).visit(type);
        source.write(writer);
    }
}
