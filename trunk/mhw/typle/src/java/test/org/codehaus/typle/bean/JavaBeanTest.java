/*
 * $Id$
 */

package org.codehaus.typle.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

import junit.framework.TestCase;

import org.codehaus.typle.Naming;
import org.codehaus.typle.RecordType;
import org.codehaus.typle.XmlStoreTypeFactory;
import org.codehaus.typle.test.SingleField;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class JavaBeanTest extends TestCase {
    private static final String NS = "tests";

    protected void setUp() throws Exception {
        super.setUp();
        URL testDirUrl = JavaBeanTest.class.getResource("/");
        File testDir = new File(testDirUrl.getFile());
        Naming.addNamespaceFactory(NS, new XmlStoreTypeFactory(testDir));
    }

    public void testSingleFieldAsClass() throws Exception {
        RecordType type;
        JavaBean b;
        StringWriter output;

        type = (RecordType) Naming.lookup(NS, SingleField.class.getName());
        b = new JavaBean(type.getTypeName(), type, JavaBeanType.CLASS);
        output = new StringWriter();
        b.generate(new PrintWriter(output));
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
