/*
 * $Id$
 */

package org.codehaus.typle;

import junit.framework.TestCase;

/**
 * Tests for the Java type name helper methods in {@link JavaNames}.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class JavaNamesTest extends TestCase {

    private String className;

    private String packageName;

    private String fullyQualifiedClass;

    protected void setUp() throws Exception {
        super.setUp();
        className = "TheClassName";
        packageName = "org.codehaus.typle";
        fullyQualifiedClass = packageName + "." + className;
    }

    public void testPackageName() {
        assertEquals("", JavaNames.packageName(className));
        assertEquals(packageName, JavaNames.packageName(fullyQualifiedClass));
    }

    public void testClassName() {
        assertEquals(className, JavaNames.className(fullyQualifiedClass));
    }

    public void testToCamelCase() {
        assertEquals("testToCamelCase",
                            JavaNames.toCamelCase("test-to-camel-case"));
        assertEquals("testToCamelCase",
                            JavaNames.toCamelCase("testToCamel-case"));
        assertEquals("int", JavaNames.toCamelCase("int"));
    }

    public void testUpperCaseFirstLetter() {
        assertEquals("Foo", JavaNames.upperCaseFirstLetter("foo"));
        assertEquals("Foo", JavaNames.upperCaseFirstLetter("Foo"));
        assertEquals("FoO", JavaNames.upperCaseFirstLetter("foO"));
    }

    public void testLowerCaseFirstLetter() {
        assertEquals("foo", JavaNames.lowerCaseFirstLetter("foo"));
        assertEquals("foo", JavaNames.lowerCaseFirstLetter("Foo"));
        assertEquals("foO", JavaNames.lowerCaseFirstLetter("foO"));
    }
}
