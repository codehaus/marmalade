/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class XmlStoreTypeFactoryTest extends AbstractTypleTest {

    public void testLookupFailure() throws Exception {
        Type t;

        t = Naming.lookup(NS, "does.not.Exist");
        assertNull(t);
    }

    public void testSingleField() throws Exception {
        Type t;
        RecordType r;

        t = Naming.lookup(NS, "org.codehaus.typle.test.SingleField");
        assertNotNull(t);
        assertTrue("SingleField not annotated", t instanceof AnnotatedType);
        assertEquals("org.codehaus.typle.test.SingleField", t.getTypeName());
        assertEquals("[int theField]", t.toString());
        t = ((AnnotatedType) t).getAnnotatedType();
        assertTrue("SingleField not record", t instanceof RecordType);
        r = (RecordType) t;
        assertEquals(1, r.getFields().size());
        assertEquals(Java.INT_TYPE, r.getField("theField"));
    }

    public void testMultipleField() throws Exception {
        Type t;
        RecordType r;
        Type f;

        t = Naming.lookup(NS, "org.codehaus.typle.test.MultipleField");
        assertNotNull(t);
        assertTrue("MultipleField not annotated", t instanceof AnnotatedType);
        assertEquals("org.codehaus.typle.test.MultipleField", t.getTypeName());
        t = ((AnnotatedType) t).getAnnotatedType();
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
