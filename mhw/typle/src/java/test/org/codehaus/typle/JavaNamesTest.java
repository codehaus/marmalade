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
}
