/*
 * $Id$
 */

package org.codehaus.tagalog;

import junit.framework.TestCase;

/**
 * TagMapTest
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class TagMapTest extends TestCase {

    private TagMap tagMap;

    protected void setUp() throws Exception {
        super.setUp();

        tagMap = new TagMap();
        tagMap.registerTag("rod", MockRodTag.class);
        tagMap.registerTag("jane", MockTag.class);
    }

    public void testRegisterTag() {
        try {
            tagMap.registerTag(null, MockTag.class);
            fail("registered null tag name");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            tagMap.registerTag("freddy", null);
            fail("registered null tag");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            tagMap.registerTag(null, null);
            fail("registered null tag");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            tagMap.registerTag("freddy", String.class);
            fail("registered class that does not implement tag");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public void testLookupTag() {
        Class tagClass;

        try {
            tagClass = tagMap.lookupTag(null);
            fail("looked up null tag");
        } catch (NullPointerException e) {
            // expected
        }

        tagClass = tagMap.lookupTag("rod");
        assertEquals(MockRodTag.class, tagClass);
        tagClass = tagMap.lookupTag("jane");
        assertEquals(MockTag.class, tagClass);
        tagClass = tagMap.lookupTag("freddy");
        assertNull(tagClass);
    }
}
