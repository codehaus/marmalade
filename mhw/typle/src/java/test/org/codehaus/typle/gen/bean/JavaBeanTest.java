/*
 * $Id$
 */

package org.codehaus.typle.gen.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.codehaus.typle.AbstractTypleTest;
import org.codehaus.typle.Naming;
import org.codehaus.typle.Type;
import org.codehaus.typle.test.MultipleField;
import org.codehaus.typle.test.SingleField;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class JavaBeanTest extends AbstractTypleTest {
    public void testSingleFieldAsClass() throws Exception {
        Type type;
        JavaBeanGenerator b;
        StringWriter output;

        type = Naming.lookup(NS, SingleField.class.getName());
        new JavaBeanAnnotator(JavaBeanType.CLASS).visit(type);
        b = new JavaBeanGenerator();
        output = new StringWriter();
        b.generate(type, new PrintWriter(output));
        assertContentSame(type.getTypeName(), output.toString());
    }

    public void testMultipleFieldAsClass() throws Exception {
        Type type;
        JavaBeanGenerator b;
        StringWriter output;

        type = Naming.lookup(NS, MultipleField.class.getName());
        new JavaBeanAnnotator(JavaBeanType.CLASS).visit(type);
        b = new JavaBeanGenerator();
        output = new StringWriter();
        b.generate(type, new PrintWriter(output));
        assertContentSame(type.getTypeName(), output.toString());
    }

    private void assertContentSame(String typeName, String content)
        throws IOException
    {
        String sourceFileName = "/" + typeName.replace('.', File.separatorChar)
                                + ".java";
        InputStream s = JavaBeanTest.class.getResourceAsStream(sourceFileName);
        InputStreamReader r = new InputStreamReader(s);
        BufferedReader expected = new BufferedReader(r);
        String[] contentLines = content.split("\n");
        boolean pass = false;

        try {
            for (int i = 0; i < contentLines.length; i++) {
                String contentLine = contentLines[i];
                String expectedLine = expected.readLine();
                assertNotNull(expectedLine);
                assertEquals(expectedLine, contentLine);
            }
            assertNull(expected.readLine());
            pass = true;
        } finally {
            if (!pass) {
                System.err.println("Content mismatch. Actual output was:");
                System.err.println(content);
            }
        }
    }
}
