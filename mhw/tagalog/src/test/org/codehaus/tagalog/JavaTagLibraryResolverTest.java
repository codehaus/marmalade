/*
 * $Id$
 */

package org.codehaus.tagalog;

import junit.framework.TestCase;

/**
 * Test the {@link JavaTagLibraryResolver}.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class JavaTagLibraryResolverTest extends TestCase {
    public void testJavaTagLibraryResolver() {
        ParserConfiguration p = new ParserConfiguration();
        p.addTagLibraryResolver(new JavaTagLibraryResolver());

        TagLibrary t = p.findTagLibrary("java:org.codehaus.tagalog.MockTagLibrary");
        assertNotNull(t);
        assertTrue(t instanceof MockTagLibrary);
    }
}
