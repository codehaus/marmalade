/*
 * $Id$
 */

package org.codehaus.typle.src.java;

import java.io.PrintWriter;
import java.util.Iterator;

import junit.framework.TestCase;

import org.codehaus.typle.src.BoilerPlateComment;
import org.codehaus.typle.src.SourceFile;

/**
 * Tests for the Java source representation package.
 *
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
     * Check that boilerplate comments work.
     */
    public void testBoilerPlateComment() {
        src.add(new BoilerPlateComment("/* Header */", 2));
        src.add(new BoilerPlateComment("/* Date */", 10));
        src.add(new BoilerPlateComment("/* Id */", 5));
        src.add(new BoilerPlateComment("/* Do not edit */", 1));

        Iterator iter = src.iterator();
        BoilerPlateComment c;
        assertTrue(iter.hasNext());
        c = (BoilerPlateComment) iter.next();
        assertEquals("/* Do not edit */", c.getCommentSource());
        assertEquals(1, c.getPriority());

        assertTrue(iter.hasNext());
        c = (BoilerPlateComment) iter.next();
        assertEquals("/* Header */", c.getCommentSource());
        assertEquals(2, c.getPriority());

        assertTrue(iter.hasNext());
        c = (BoilerPlateComment) iter.next();
        assertEquals("/* Id */", c.getCommentSource());
        assertEquals(5, c.getPriority());

        assertTrue(iter.hasNext());
        c = (BoilerPlateComment) iter.next();
        assertEquals("/* Date */", c.getCommentSource());
        assertEquals(10, c.getPriority());

        assertFalse(iter.hasNext());
    }

    /**
     * Check that package declarations work.
     */
    public void testPackage() {
        src.add(new JavaPackage("abc.def.ghi"));

        Iterator iter = src.iterator();
        JavaPackage p;
        assertTrue(iter.hasNext());
        p = (JavaPackage) iter.next();
        assertEquals("abc.def.ghi", p.getPackageName());
        assertFalse(iter.hasNext());
    }

    /**
     * Check that duplication package declarations override each other.
     */
    public void testPackageDuplication() {
        src.add(new JavaPackage("abc.def.ghi"));
        src.add(new JavaPackage("def.pqr.xyz"));

        Iterator iter = src.iterator();
        JavaPackage p;
        assertTrue(iter.hasNext());
        p = (JavaPackage) iter.next();
        assertEquals("def.pqr.xyz", p.getPackageName());
        assertFalse(iter.hasNext());

        src.add(new JavaPackage("abc.def.pqr"));

        iter = src.iterator();
        assertTrue(iter.hasNext());
        p = (JavaPackage) iter.next();
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
     * Test ordering of top-level java constructs.
     */
    public void testJavaConstructOrder() {
        src.add(new JavaClass(Modifier.PRIVATE, "Abc"));
        src.add(new JavaClass("Xyz"));
        src.add(new Import("org.codehaus.Foo"));
        src.add(new JavaPackage("org.codehaus.typle"));
        src.add(new BoilerPlateComment("/* Header */", 1));

        Iterator iter = src.iterator();
        Object o;
        JavaClass c;
        assertTrue(iter.hasNext());
        o = iter.next();
        assertEquals(BoilerPlateComment.class, o.getClass());
        assertTrue(iter.hasNext());
        o = iter.next();
        assertEquals(JavaPackage.class, o.getClass());
        assertTrue(iter.hasNext());
        o = iter.next();
        assertEquals(Import.class, o.getClass());
        assertTrue(iter.hasNext());
        o = iter.next();
        assertEquals(JavaClass.class, o.getClass());
        c = (JavaClass) o;
        assertEquals("Xyz", c.getClassName());
        assertTrue(iter.hasNext());
        o = iter.next();
        assertEquals(JavaClass.class, o.getClass());
        c = (JavaClass) o;
        assertEquals("Abc", c.getClassName());
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
    public void testJavaClassOrdering1() {
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

    /**
     * Verify that Java classes are ordered correctly: public first, then
     * others in alphabetical order. Additional test to hit a different
     * branch in javaClassCompare.
     */
    public void testJavaClassOrdering2() {
        src.add(new JavaClass(Modifier.PRIVATE, "Abc"));
        src.add(new JavaClass("Def"));

        Iterator iter = src.iterator();
        JavaClass c;
        assertTrue(iter.hasNext());
        c = (JavaClass) iter.next();
        assertEquals("Def", c.getClassName());
        assertTrue(iter.hasNext());
        c = (JavaClass) iter.next();
        assertEquals("Abc", c.getClassName());
        assertFalse(iter.hasNext());
    }
}
