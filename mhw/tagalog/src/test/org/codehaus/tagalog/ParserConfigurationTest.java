/*
 * $Id$
 */

package org.codehaus.tagalog;

import junit.framework.TestCase;

/**
 * Tests for the {@link ParserConfiguration} class.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class ParserConfigurationTest extends TestCase {
    /*
     * Test for default constructor.
     */
    public void testDefaultConstructor() {
        ParserConfiguration p = new ParserConfiguration();
        TagLibrary tagLibrary = new MockTagLibrary();
        p.addTagLibrary("some-uri", tagLibrary);
        assertSame(tagLibrary, p.findTagLibrary("some-uri"));
    }

    /*
     * Test for AbstractParser(FallbackTagLibraryResolver).
     */
    public void testConstructorWithResolver() {
        MockFallbackTagLibraryResolver resolver = new MockFallbackTagLibraryResolver();
        ParserConfiguration p = new ParserConfiguration(resolver);
        TagLibrary tagLibrary = new MockTagLibrary();
        assertFalse(resolver.addTagLibraryCalled);
        p.addTagLibrary("some-uri", tagLibrary);
        assertTrue(resolver.addTagLibraryCalled);
        assertFalse(resolver.resolveCalled);
        assertNull(p.findTagLibrary("some-uri"));
        assertTrue(resolver.resolveCalled);
    }

    public void testAddTagLibrary() {
        ParserConfiguration p = new ParserConfiguration();

        try {
            p.addTagLibrary(null, new MockTagLibrary());
            fail("added tag library with null URI");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            p.addTagLibrary("", new MockTagLibrary());
            fail("added tag library with empty URI");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            p.addTagLibrary("some-uri", null);
            fail("added null tag library");
        } catch (NullPointerException e) {
            // expected
        }

        TagLibrary t1 = new MockTagLibrary();
        TagLibrary t2 = new MockTagLibrary();

        assertNull(p.findTagLibrary("some-uri"));
        p.addTagLibrary("some-uri", t1);
        assertEquals(t1, p.findTagLibrary("some-uri"));
        p.addTagLibrary("some-uri", t2);
        assertEquals(t2, p.findTagLibrary("some-uri"));

        TagLibrary t3 = new MockTagLibrary();

        assertNull(p.findTagLibrary("some-other-uri"));
        p.addTagLibrary("some-other-uri", t3);
        assertEquals(t2, p.findTagLibrary("some-uri"));
        assertEquals(t3, p.findTagLibrary("some-other-uri"));
        assertNull(p.findTagLibrary("unregistered"));
    }

    public void testAddTagLibraryResolver() {
        ParserConfiguration p = new ParserConfiguration();

        try {
            p.addTagLibraryResolver(new MockPrefixTagLibraryResolver(null, null));
            fail("added tag library resolver with null URI prefix");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            p.addTagLibraryResolver(new MockPrefixTagLibraryResolver("", null));
            fail("added tag library resolver with empty URI prefix");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            p.addTagLibraryResolver(null);
            fail("added null tag library resolver");
        } catch (NullPointerException e) {
            // expected
        }

        
        TagLibrary t1 = new MockTagLibrary();
        TagLibrary t2 = new MockTagLibrary();
        PrefixTagLibraryResolver r1 = new MockPrefixTagLibraryResolver("prefix", t1);
        PrefixTagLibraryResolver r2 = new MockPrefixTagLibraryResolver("prefix", t2);

        assertNull(p.findTagLibrary("prefix:suffix"));
        p.addTagLibraryResolver(r1);
        assertEquals(t1, p.findTagLibrary("prefix:suffix"));
        p.addTagLibraryResolver(r2);
        assertEquals(t2, p.findTagLibrary("prefix:suffix"));

        TagLibrary t3 = new MockTagLibrary();
        PrefixTagLibraryResolver r3 = new MockPrefixTagLibraryResolver("jelly", t3);

        assertNull(p.findTagLibrary("jelly:core"));
        p.addTagLibraryResolver(r3);
        assertEquals(t2, p.findTagLibrary("prefix:suffix"));
        assertEquals(t3, p.findTagLibrary("jelly:core"));
        assertNull(p.findTagLibrary("unregistered"));
    }

    public void testFindTagLibrary() {
        ParserConfiguration p = new ParserConfiguration();
        TagLibrary t1 = new MockTagLibrary();

        p.addTagLibrary("some-uri", t1);
        assertEquals(t1, p.findTagLibrary("some-uri"));
        assertNull(p.findTagLibrary("unregistered"));

        try {
            p.findTagLibrary(null);
            fail("findTagLibrary(null) returned");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            p.findTagLibrary("");
            fail("findTagLibrary(\"\") returned");
        } catch (IllegalArgumentException e) {
            // expected
        }

        p.setDefaultNamespace("some-uri");
        assertEquals(t1, p.findTagLibrary(""));
    }
}
