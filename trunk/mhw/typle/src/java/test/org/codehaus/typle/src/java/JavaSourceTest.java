/*
 * $Id$
 */

package org.codehaus.typle.src.java;

import java.io.PrintWriter;
import java.util.Iterator;

import junit.framework.TestCase;

import org.codehaus.typle.src.SourceFile;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class JavaSourceTest extends TestCase {
    private SourceFile src;

    protected void setUp() throws Exception {
        super.setUp();
        src = new SourceFile(new PrintWriter(System.out),
                             JavaSource.COMPARATOR);
    }

    /**
     * Check that package declarations work.
     */
    public void testPackage() {
        src.add(new Package("abc.def.ghi"));

        Iterator iter = src.iterator();
        Package p;
        assertTrue(iter.hasNext());
        p = (Package) iter.next();
        assertEquals("abc.def.ghi", p.getPackageName());
        assertFalse(iter.hasNext());
    }

    /**
     * Check that duplication package declarations override each other.
     */
    public void testPackageDuplication() {
        src.add(new Package("abc.def.ghi"));
        src.add(new Package("def.pqr.xyz"));

        Iterator iter = src.iterator();
        Package p;
        assertTrue(iter.hasNext());
        p = (Package) iter.next();
        assertEquals("def.pqr.xyz", p.getPackageName());
        assertFalse(iter.hasNext());

        src.add(new Package("abc.def.pqr"));

        iter = src.iterator();
        assertTrue(iter.hasNext());
        p = (Package) iter.next();
        assertEquals("abc.def.pqr", p.getPackageName());
        assertFalse(iter.hasNext());
    }

    /**
     * Check that imports are sorted into order.
     */
    public void testImport() {
        src.add(new Import("abc.Class"));
        src.add(new Import("xyz.Class"));
        src.add(new Import("mno.Class"));
        src.add(new Import("ghi.Class"));

        Iterator iter = src.iterator();
        Import i;
        assertTrue(iter.hasNext());
        i = (Import) iter.next();
        assertEquals("abc.Class", i.getImportSpecification());
        assertTrue(iter.hasNext());
        i = (Import) iter.next();
        assertEquals("ghi.Class", i.getImportSpecification());
        assertTrue(iter.hasNext());
        i = (Import) iter.next();
        assertEquals("mno.Class", i.getImportSpecification());
        assertTrue(iter.hasNext());
        i = (Import) iter.next();
        assertEquals("xyz.Class", i.getImportSpecification());
        assertFalse(iter.hasNext());
    }

    /**
     * Check that duplicate imports are override correctly.
     */
    public void testImportDuplicates() {
        src.add(new Import("xyz.Class"));
        src.add(new Import("mno.Class"));
        src.add(new Import("mno.Class"));
        src.add(new Import("xyz.Class"));

        Iterator iter = src.iterator();
        Import i;
        assertTrue(iter.hasNext());
        i = (Import) iter.next();
        assertEquals("mno.Class", i.getImportSpecification());
        assertTrue(iter.hasNext());
        i = (Import) iter.next();
        assertEquals("xyz.Class", i.getImportSpecification());
        assertFalse(iter.hasNext());
    }

    /**
     * Test ordering of public java classes, despite the fact that the
     * resulting source file would be invalid.
     */
    public void testPublicJavaClasses() {
        src.add(new JavaClass("Def"));
        src.add(new JavaClass("Abc"));
        src.add(new JavaClass("Xyz"));

        Iterator iter = src.iterator();
        JavaClass c;
        assertTrue(iter.hasNext());
        c = (JavaClass) iter.next();
        assertEquals("Abc", c.getClassName());
        assertTrue(iter.hasNext());
        c = (JavaClass) iter.next();
        assertEquals("Def", c.getClassName());
        assertTrue(iter.hasNext());
        c = (JavaClass) iter.next();
        assertEquals("Xyz", c.getClassName());
        assertFalse(iter.hasNext());
    }

    /**
     * Verify that Java classes are ordered correctly: public first, then
     * others in alphabetical order.
     */
    public void testJavaClassOrdering() {
        src.add(new JavaClass("Def"));
        src.add(new JavaClass(Modifier.PRIVATE, "Abc"));
        src.add(new JavaClass(Modifier.PRIVATE, "Xyz"));
        src.add(new JavaClass(Modifier.PRIVATE, "Mno"));

        Iterator iter = src.iterator();
        JavaClass c;
        assertTrue(iter.hasNext());
        c = (JavaClass) iter.next();
        assertEquals("Def", c.getClassName());
        assertTrue(iter.hasNext());
        c = (JavaClass) iter.next();
        assertEquals("Abc", c.getClassName());
        assertTrue(iter.hasNext());
        c = (JavaClass) iter.next();
        assertEquals("Mno", c.getClassName());
        assertTrue(iter.hasNext());
        c = (JavaClass) iter.next();
        assertEquals("Xyz", c.getClassName());
        assertFalse(iter.hasNext());
    }
}
