/*
 * $Id$
 */

package org.codehaus.typle.tools;

import junit.framework.TestCase;

/**
 * Tests for the Bean Builder.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public class BeanBuilderTest extends TestCase {

    public void testBeanBuilder() throws Exception {
        BeanBuilder b = new BeanBuilder();
        b.run();
    }
}
