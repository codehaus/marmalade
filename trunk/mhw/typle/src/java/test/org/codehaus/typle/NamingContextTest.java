/*
 * $Id$
 */

package org.codehaus.typle;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Tests for {@link NamingContext} objects.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public class NamingContextTest extends TestCase {
    private NamingContext base;
    private NamingContext nc;

    private String[] data = new String[] {
        "base",
        "foo",
        "bar",
        "baz",
    };
    
    protected void setUp() throws Exception {
        super.setUp();
        base = new NamingContext(data[0]);
        nc = base.addNamespace(data[1]);
        nc = nc.addNamespace(data[2]);
        nc = nc.addNamespace(data[3]);
    }

    /**
     * Test the simple one-element <code>NamingContext</code>.
     */
    public void testSimpleIteration() {
        Iterator iter = base.iterator();
        assertNotNull(iter);
        while (iter.hasNext()) {
            String namespace = (String) iter.next();
            assertEquals(data[0], namespace);
        }
    }

    /**
     * Test the four-element <code>NamingContext</code>.
     */
    public void testIteration() {
        Iterator iter = base.iterator();
        assertNotNull(iter);
        int i = 0;
        while (iter.hasNext()) {
            String namespace = (String) iter.next();
            assertEquals(data[i++], namespace);
        }
    }
}
