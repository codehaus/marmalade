/*
 * $Id$
 */

package org.codehaus.typle.tools;

import java.io.IOException;
import java.io.PrintWriter;

import org.codehaus.typle.AnnotatedType;
import org.codehaus.typle.Binding;
import org.codehaus.typle.Java;
import org.codehaus.typle.JavaReferenceType;
import org.codehaus.typle.RecordType;
import org.codehaus.typle.Type;
import org.codehaus.typle.gen.bean.JavaBean;
import org.codehaus.typle.gen.bean.JavaBeanGenerator;
import org.codehaus.typle.gen.bean.JavaBeanType;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class BeanBuilder {
    public void run() throws IOException {
        Type record = new RecordType(new Binding[] {
            new Binding("intField", Java.INT_TYPE),
            new Binding("stringField",
                          new JavaReferenceType("java.lang.String")),
            new Binding("fooField",
                          new JavaReferenceType("org.codehaus.Foo")),
        });
        record = new AnnotatedType(record);
        record.addAnnotation(new JavaBean("Record", JavaBeanType.CLASS));
        JavaBeanGenerator generator = new JavaBeanGenerator();
        generator.generate(record, new PrintWriter(System.out, true));
    }
}
