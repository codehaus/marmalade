/*
 * $Id$
 */

package org.codehaus.typle.src.java;

import junit.framework.TestCase;

/**
 * Tests for {@link Modifier}.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class ModifierTest extends TestCase {
    public void testToStringModifierArray() {
        assertCorrectOrder("public static final",
            new Modifier[] {
                Modifier.FINAL, Modifier.STATIC, Modifier.PUBLIC,
            });
        assertCorrectOrder("public final",
            new Modifier[] {
                Modifier.FINAL, Modifier.PUBLIC,
            });
        assertCorrectOrder("public",
            new Modifier[] {
                Modifier.PUBLIC,
            });

        // section 8.1.1 - class modifiers
        assertCorrectOrder("public protected private",
            new Modifier[] {
                Modifier.PROTECTED, Modifier.PRIVATE, Modifier.PUBLIC,
            });
        assertCorrectOrder("abstract static final strictfp",
            new Modifier[] {
                Modifier.FINAL, Modifier.STATIC,
                Modifier.STRICTFP, Modifier.ABSTRACT,
            });

        // section 8.3.1 - field modifiers
        assertCorrectOrder("static final transient volatile",
            new Modifier[] {
                Modifier.FINAL, Modifier.STATIC,
                Modifier.VOLATILE, Modifier.TRANSIENT,
            });

        // section 8.3.1 - method modifiers
        assertCorrectOrder("abstract static final synchronized native strictfp",
            new Modifier[] {
                Modifier.FINAL, Modifier.STATIC, Modifier.ABSTRACT,
                Modifier.NATIVE, Modifier.SYNCHRONIZED, Modifier.STRICTFP,
            });
    }

    private void assertCorrectOrder(String expected, Modifier[] modifiers) {
        assertEquals(expected, Modifier.toString(modifiers));
    }
}
