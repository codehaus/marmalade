/*
 * $Id$
 */

package org.codehaus.tagalog;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.codehaus.tagalog.acceptance.people.AttributeTag;
import org.codehaus.tagalog.acceptance.people.PeopleTagLibrary;
import org.codehaus.tagalog.acceptance.people.PersonTag;

import junit.framework.TestCase;

/**
 * Tests for {@link AbstractTagLibrary}, using the {@link MockTagLibrary}
 * subclass.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class AbstractTagLibraryTest extends TestCase {

    public void testRegisterTag() throws Exception {
        TagLibrary tagLibrary;
        Tag tag;

        try {
            tagLibrary = new MockTagLibrary(null, MockTag.class);
            fail("registered tag for null tag name");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            tagLibrary = new MockTagLibrary("", MockTag.class);
            fail("registered tag for empty tag name");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            tagLibrary = new MockTagLibrary("fred", null);
            fail("registered null tag");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            tagLibrary = new MockTagLibrary("fred", String.class);
            fail("registered tag that doesn't implement Tag interface");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            tagLibrary = new MockTagLibrary("fred",
                                        InterfaceThatImplementsTag.class);
            fail("registered tag that is interface");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            tagLibrary = new MockTagLibrary("fred",
                                        AbstractClassThatImplementsTag.class);
            fail("registered tag that is abstract");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            tagLibrary = new MockTagLibrary("fred",
                                        PrivateClassThatImplementsTag.class);
            fail("registered tag that is private");
        } catch (IllegalArgumentException e) {
            // expected
        }

        tagLibrary = new MockTagLibrary("fred", MockTag.class);
        tag = tagLibrary.getTag("fred");
        assertTrue(tag instanceof MockTag);
    }

    public void testGetTag() {
        TagLibrary tagLibrary = new PeopleTagLibrary();
        Tag tag1, tag2, tag3;

        tag1 = tagLibrary.getTag("person");
        assertTrue(tag1 instanceof PersonTag);
        tag2 = tagLibrary.getTag("person");
        assertTrue(tag2 instanceof PersonTag);
        assertNotSame(tag1, tag2);
        tag3 = tagLibrary.getTag("person");
        assertTrue(tag3 instanceof PersonTag);
        assertNotSame(tag1, tag3);
        assertNotSame(tag2, tag3);

        tag1 = tagLibrary.getTag("first-name");
        assertTrue(tag1 instanceof AttributeTag);
        tag2 = tagLibrary.getTag("last-name");
        assertTrue(tag2 instanceof AttributeTag);
        assertNotSame(tag1, tag2);
        tag3 = tagLibrary.getTag("first-name");
        assertTrue(tag3 instanceof AttributeTag);
        assertNotSame(tag1, tag3);
        assertNotSame(tag2, tag3);

        assertNull(tagLibrary.getTag("does-not-exist"));

        try {
            tagLibrary.getTag(null);
            fail("got tag with null tag name");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            tagLibrary.getTag("");
            fail("got tag with empty tag name");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public void testReleaseTag() {
        AbstractTagLibrary tagLibrary = new PeopleTagLibrary();
        Set tags = new HashSet();

        for (int i = 0; i < 5; i++) {
            tags.add(tagLibrary.getTag("person"));
        }
        assertEquals(5, tags.size());
        assertEquals("person: 5", tagLibrary.listUnreleasedTags());

        Iterator iter = tags.iterator();
        while (iter.hasNext()) {
            Tag tag = (Tag) iter.next();
            tagLibrary.releaseTag("person", tag);
        }
        assertEquals("", tagLibrary.listUnreleasedTags());

        for (int i = 0; i < 5; i++) {
            Tag tag = tagLibrary.getTag("person");
            assertTrue(tags.remove(tag));
        }
        assertEquals(0, tags.size());
        assertEquals("person: 5", tagLibrary.listUnreleasedTags());
    }

    public void testTagRecycling() {
        ParserConfiguration p = new ParserConfiguration();
        AbstractTagLibrary tagLibrary;
        Tag tag1, tag2;

        p.addTagLibrary("recycle", new MockTagLibrary("tag", MockTag.class));
        p.addTagLibrary("discard", new MockTagLibrary("tag", MockRodTag.class));

        tagLibrary = (AbstractTagLibrary) p.findTagLibrary("recycle");
        tag1 = tagLibrary.getTag("tag");
        assertNotNull(tag1);
        tagLibrary.releaseTag("tag", tag1);
        tag2 = tagLibrary.getTag("tag");
        assertSame(tag1, tag2);
        tagLibrary.releaseTag("tag", tag2);
        assertEquals("", tagLibrary.listUnreleasedTags());

        tagLibrary = (AbstractTagLibrary) p.findTagLibrary("discard");
        tag1 = tagLibrary.getTag("tag");
        assertNotNull(tag1);
        tagLibrary.releaseTag("tag", tag1);
        tag2 = tagLibrary.getTag("tag");
        assertNotSame(tag1, tag2);
        tagLibrary.releaseTag("tag", tag2);
        assertEquals("", tagLibrary.listUnreleasedTags());
    }
    
    //
    // Some implementations of Tag that cannot be instantiated
    //

    public interface InterfaceThatImplementsTag extends Tag {
    }

    public abstract class AbstractClassThatImplementsTag implements Tag {
    }

    private class PrivateClassThatImplementsTag extends AbstractTag {
    }
}
