/*
 * $Id$
 */

package org.codehaus.typle.util;

import junit.framework.TestCase;

/**
 * Tests for {@link SAXParserState}.
 * 
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class SAXParserStateTest extends TestCase {

    private SAXParserState state;

    private char[] chars;

    private char[] whitespace;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        state = new SAXParserState();
        state.startElement(null, null, "a");
        state.startElement(null, null, "b");
        state.startElement(null, null, "c");
        state.startElement(null, null, "d");
        chars = "foobar".toCharArray();
        whitespace = "\n\t ".toCharArray();
    }

    public void testInElement() {
        assertFalse(state.inElement("a"));
        assertFalse(state.inElement("b"));
        assertFalse(state.inElement("c"));
        assertTrue(state.inElement("d"));
        assertFalse(state.inElement("x"));

        assertFalse(state.inElement("a", "b"));
        assertFalse(state.inElement("b", "c"));
        assertTrue(state.inElement("c", "d"));
        assertFalse(state.inElement("b", "d"));
        assertFalse(state.inElement("x", "d"));
        assertFalse(state.inElement("c", "x"));
    }

    public void testStartElement() {
        state.startElement(null, null, "x");
        assertTrue(state.inElement("x"));
        state.startElement(null, null, "y");
        assertFalse(state.inElement("x"));
        assertTrue(state.inElement("y"));
        state.startElement(null, null, "z");
        assertFalse(state.inElement("x"));
        assertFalse(state.inElement("y"));
        assertTrue(state.inElement("z"));
    }

    public void testEndElement() {
        try {
            state.endElement(null, null, "a");
            fail("managed to remove element 'a'");
        } catch (Throwable t) {
        }
        try {
            state.endElement(null, null, "d");
        } catch (Throwable t) {
            fail("caught unexpected exception " + t);
        }
    }

    /*
     * Test for boolean containedBy(String)
     */
    public void testContainedByString() {
        assertTrue(state.containedBy("a"));
        assertTrue(state.containedBy("b"));
        assertTrue(state.containedBy("c"));
        assertTrue(state.containedBy("d"));
        
        assertFalse(state.containedBy("x"));
        assertFalse(state.containedBy("y"));
        assertFalse(state.containedBy("z"));
    }

    /*
     * Test for boolean containedBy(String, String)
     */
    public void testContainedByStringString() {
        assertTrue(state.containedBy("a", "b"));
        assertTrue(state.containedBy("b", "d"));
        assertTrue(state.containedBy("c", "d"));

        assertFalse(state.containedBy("d", "a"));
        assertFalse(state.containedBy("d", "b"));
        assertFalse(state.containedBy("c", "a"));
        assertFalse(state.containedBy("c", "b"));
    }

    /*
     * Check that mixed-content is correctly rejected.
     */
    public void testMixedContent1() {
        state.characters(chars, 0, chars.length);
        try {
            state.startElement(null, null, "e");
            fail("allowed mixed content - chars followed by element");
        } catch (IllegalStateException e) {
            // ignore
        }
    }

    /*
     * Check that mixed-content is correctly rejected.
     */
    public void testMixedContent2() {
        state.startElement(null, null, "e");
        state.endElement(null, null, "e");
        try {
            state.characters(chars, 0, chars.length);
            fail("allowed mixed content - element followed by chars");
        } catch (IllegalStateException e) {
            // ignore
        }
    }

    public void testContent() {
        state.characters(chars, 0, chars.length);
        state.characters(chars, 0, chars.length);
        state.characters(chars, 0, chars.length);
        assertEquals("foobarfoobarfoobar", state.getCharacters());
    }

    /*
     * Make sure that insignificant whitespace is ignored correctly.
     */
    public void testSignificantCharacters() {
        state.characters(whitespace, 0, whitespace.length);
        state.startElement(null, null, "e");
        state.characters(whitespace, 0, whitespace.length);
        state.characters(chars, 0, chars.length);
        state.characters(whitespace, 0, whitespace.length);
        assertEquals("\n\t foobar\n\t ", state.getCharacters());
        state.endElement(null, null, "e");
        state.characters(whitespace, 0, whitespace.length);
        try {
            String s = state.getCharacters();
            fail("managed to get mixed content - " + s);
        } catch (IllegalStateException e) {
            // ignore
        }
    }

    public void testSimpleScope() throws Exception {
        assertEquals(null, state.get("foo"));
        assertEquals(null, state.get("baz"));
        state.put("foo", "bar");
        assertEquals("bar", state.get("foo"));
        assertEquals(null, state.get("baz"));
        state.put("baz", "wibble");
        assertEquals("bar", state.get("foo"));
        assertEquals("wibble", state.get("baz"));
    }

    public void testNestedScope() throws Exception {
        assertEquals(null, state.get("foo"));
        assertEquals(null, state.get("baz"));
        state.put("foo", "bar");
        state.put("baz", "wibble");
        assertEquals("bar", state.get("foo"));
        assertEquals("wibble", state.get("baz"));
        state.openScope();
        assertEquals(null, state.get("foo"));
        assertEquals(null, state.get("baz"));
        state.put("foo", "bar");
        state.put("frob", "wibble");
        assertEquals("bar", state.get("foo"));
        assertEquals("wibble", state.get("frob"));
        assertEquals(null, state.get("baz"));
        state.closeScope();
        assertEquals("bar", state.get("foo"));
        assertEquals("wibble", state.get("baz"));
        assertEquals(null, state.get("frob"));
    }
}
