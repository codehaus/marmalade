/*
 * $Id$
 */

package org.codehaus.typle;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

/**
 * Abstract base class for tests that load type definitions from the
 * test-input source directory.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public abstract class AbstractTypleTest extends TestCase {
    public static final String NS = "tests";

    protected void setUp() throws Exception {
        URL testDirUrl;
        File testDir;
        XmlStoreTypeFactory factory;

        super.setUp();
        testDirUrl = AbstractTypleTest.class.getResource("/");
        testDir = new File(testDirUrl.getFile());
        factory = new XmlStoreTypeFactory(testDir);
        factory.setSupportAnnotations(true);
        Naming.addNamespaceFactory(NS, factory);
    }
}
