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
import org.codehaus.typle.src.BoilerPlateComment;
import org.codehaus.typle.src.SourceFile;
import org.codehaus.typle.src.java.Field;
import org.codehaus.typle.src.java.Import;
import org.codehaus.typle.src.java.JavaClass;
import org.codehaus.typle.src.java.JavaPackage;
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
     * @return
     */
    public RecordType getType() {
        return type;
    }

    /**
     * @return
     */
    public JavaBeanType getJavaBeanType() {
        return javaBeanType;
    }

    /**
     * @param stream
     */
    public void generate(PrintWriter writer) throws IOException {
        SourceFile source = new SourceFile(writer, JavaSource.COMPARATOR);
        State state = new State(source);
        visit(type, state);
        source.writeOutput();
    }

    private static class State {
        SourceFile source;
        JavaClass outputClass;

        public State(SourceFile source) {
            this.source = source;
        }
    }

    /**
     * @param record
     */
    private void visit(RecordType record, State state) {
        state.source.add(new BoilerPlateComment("/*\n * $" + "Id$\n */\n\n", 1));
        state.source.add(new JavaPackage("net.kremvax"));
        state.source.add(new JavaPackage("foo.kremvax"));
        state.source.add(new Import("org.codehaus.plexus.Plexus"));
        state.source.add(new Import("javax.servlet.Servlet"));

        state.outputClass = new JavaClass(beanName);
        state.source.add(state.outputClass);
        BindingList fields = record.getFields();
        for (int i = 0; i < fields.size(); i++) {
            visit(fields.get(i), state);
        }
        state.outputClass = null;
    }

    /**
     * @param binding
     */
    private void visit(Binding binding, State state) {
        Type t = binding.getType();
        if (t instanceof JavaReferenceType) {
            JavaReferenceType javaType = (JavaReferenceType) t;

            if (!t.getTypeName().startsWith("java.lang.")) {
                state.source.add(new Import(t.getTypeName()));
            }
            state.outputClass.add(new Field(javaType.getUnqualifiedName(),
                                            binding.getName()));
        } else if (t instanceof JavaPrimitive) {
            state.outputClass.add(new Field(binding.getType().getTypeName(),
                                            binding.getName()));
        }
    }
}
