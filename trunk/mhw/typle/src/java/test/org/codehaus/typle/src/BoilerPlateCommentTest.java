/*
 * $Id$
 */

package org.codehaus.typle.src;

import junit.framework.TestCase;

/**
 * Tests for {@link BoilerPlateComment} and {@link AbstractSourceArtefact}.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class BoilerPlateCommentTest extends TestCase {
    private BoilerPlateComment comment;

    private SourceFile src;

    protected void setUp() throws Exception {
        super.setUp();
        comment = new BoilerPlateComment("/* foo */", 5);
        src = new SourceFile(SimpleSourceOrdering.COMPARATOR);
    }

    /**
     * The basic accessors should return properties of the object.
     */
    public void testAccessors() {
        assertEquals("/* foo */", comment.getCommentSource());
        assertEquals(5, comment.getPriority());
    }

    /**
     * Adding an artefact to a container should set the container field.
     */
    public void testContainment() {
        assertNull(comment.getContainer());
        src.add(comment);
        assertEquals(src, comment.getContainer());
    }

    /**
     * Make sure that adding an source artefact to a container more than
     * once fails.
     */
    public void testMultipleAddition() {
        src.add(comment);
        try {
            src.add(comment);
            fail();
        } catch (IllegalStateException e) {
            // ignore
        }
    }
}
