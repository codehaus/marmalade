/*
 * $Id$
 */

package org.codehaus.typle;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class XmlStoreTypeFactoryTest extends TestCase {
    private static final String NS = "tests";

    private static final String TEST_FILE = "xml.store.test.xml";

    protected void setUp() throws Exception {
        super.setUp();
        URL testDirUrl = XmlStoreTypeFactoryTest.class.getResource("/");
        File testDir = new File(testDirUrl.getFile());
        Naming.addNamespaceFactory(NS, new XmlStoreTypeFactory(testDir));
    }

    public void testLookupFailure() throws Exception {
        Type t;

        t = Naming.lookup(NS, "does.not.Exist");
        assertNull(t);
    }

    public void testSingleField() throws Exception {
        Type t;
        RecordType r;

        t = Naming.lookup(NS, "xml.store.test.SingleField");
        assertNotNull(t);
        assertTrue("SingleField not record", t instanceof RecordType);
        r = (RecordType) t;
        assertEquals(1, r.getFields().size());
        assertEquals(Java.INT_TYPE, r.getField("theField"));
    }

    public void testMultipleField() throws Exception {
        Type t;
        RecordType r;
        Type f;

        t = Naming.lookup(NS, "xml.store.test.MultipleField");
        assertNotNull(t);
        assertTrue("MultipleField not record", t instanceof RecordType);
        r = (RecordType) t;
        assertEquals(3, r.getFields().size());
        f = r.getField("fieldOne");
        assertEquals("java.lang.String", f.getTypeName());
        f = r.getField("fieldTwo");
        assertEquals("int", f.getTypeName());
        f = r.getField("fieldThree");
        assertEquals("float", f.getTypeName());
    }
}
