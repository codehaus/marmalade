/*
 * $Id$
 */

package org.codehaus.typle;

import java.util.Map;

import org.codehaus.typle.test.SingleField;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class TypeReplacementTest extends AbstractTypleTest {
    private Type integerType;

    private Type floatType;

    private Map primitivesToObjects;

    protected void setUp() throws Exception {
        super.setUp();

        integerType = Naming.lookup("java", Integer.class.getName());
        floatType = Naming.lookup("java", Float.class.getName());

        primitivesToObjects = new java.util.HashMap();
        primitivesToObjects.put(Java.INT_TYPE, integerType);
        primitivesToObjects.put(Java.FLOAT_TYPE, floatType);
    }

    public void testSingleFieldReplacement() throws TypeLookupException {
        Type type;
        RecordType record;
        TypeReplacement r;

        type = Naming.lookup(NS, SingleField.class.getName());
        r = new TypeReplacement(primitivesToObjects);
        type = r.visit(type);
        record = (RecordType) ((AnnotatedType) type).getAnnotatedType();
        assertEquals(1, record.getFields().size());
        assertEquals(integerType, record.getField("theField"));
    }
}
