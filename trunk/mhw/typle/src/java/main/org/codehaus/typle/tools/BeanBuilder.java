/*
 * $Id$
 */

package org.codehaus.typle.tools;

import java.io.IOException;
import java.io.PrintWriter;

import org.codehaus.typle.Java;
import org.codehaus.typle.JavaReferenceType;
import org.codehaus.typle.NamedType;
import org.codehaus.typle.RecordType;
import org.codehaus.typle.bean.JavaBeanType;
import org.codehaus.typle.bean.JavaBean;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class BeanBuilder {
    public void run() throws IOException {
        RecordType record = new RecordType(new NamedType[] {
            new NamedType("intField", Java.INT_TYPE),
            new NamedType("stringField",
                          new JavaReferenceType("java.lang.String")),
            new NamedType("fooField",
                          new JavaReferenceType("org.codehaus.Foo")),
        });
        JavaBean bean = new JavaBean("Record", record, JavaBeanType.CLASS);
        bean.generate(new PrintWriter(System.out, true));
    }
}
