/*
 * $Id$
 */

package org.codehaus.typle.bean;

import java.io.IOException;
import java.io.PrintWriter;

import org.codehaus.typle.Binding;
import org.codehaus.typle.BindingList;
import org.codehaus.typle.JavaPrimitive;
import org.codehaus.typle.JavaReferenceType;
import org.codehaus.typle.RecordType;
import org.codehaus.typle.Type;
import org.codehaus.typle.src.SourceContainer;
import org.codehaus.typle.src.SourceFile;
import org.codehaus.typle.src.java.Field;
import org.codehaus.typle.src.java.Import;
import org.codehaus.typle.src.java.JavaClass;
import org.codehaus.typle.src.java.JavaHelper;
import org.codehaus.typle.src.java.JavaSource;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class JavaBean {
    private final String beanName;

    private final RecordType type;

    private final JavaBeanType javaBeanType;

    public JavaBean(String beanName, RecordType type, JavaBeanType javaBeanType)
    {
        this.beanName = beanName;
        this.type = type;
        this.javaBeanType = javaBeanType;
    }

    /**
     * @param stream
     */
    public void generate(PrintWriter writer) throws IOException {
        SourceFile source = new SourceFile(JavaSource.COMPARATOR);
        JavaClass srcClass = JavaHelper.initSourceFile(source, beanName);
        addImports(type, source);
        addFields(type, srcClass);
        addGetMethods(type, srcClass);
        addSetMethods(type, srcClass);
        source.write(writer);
    }

    private void addImports(RecordType record, SourceContainer src) {
        BindingList fields = record.getFields();
        for (int i = 0; i < fields.size(); i++) {
            Type t = fields.get(i).getType();
            if (t instanceof JavaReferenceType) {
                src.add(new Import(t.getTypeName()));
            }
        }
    }

    private void addFields(RecordType record, SourceContainer src) {
        BindingList fields = record.getFields();
        for (int i = 0; i < fields.size(); i++) {
            Binding binding = fields.get(i);
            Type t = binding.getType();
            if (t instanceof JavaReferenceType) {
                JavaReferenceType javaType = (JavaReferenceType) t;
                src.add(new Field(javaType.getUnqualifiedName(),
                                                binding.getName()));
            } else if (t instanceof JavaPrimitive) {
                src.add(new Field(binding.getType().getTypeName(),
                                                binding.getName()));
            }
        }
    }

    private void addGetMethods(RecordType record, SourceContainer src) {
    }

    private void addSetMethods(RecordType record, SourceContainer src) {
    }
}
