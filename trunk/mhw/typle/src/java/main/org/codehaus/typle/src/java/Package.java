/*
 * $Id$
 */

package org.codehaus.typle.src.java;

import java.io.IOException;
import java.io.PrintWriter;

import org.codehaus.typle.src.AbstractSourceArtefact;
import org.codehaus.typle.src.SourceArtefact;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class Package
    extends AbstractSourceArtefact
    implements SourceArtefact
{
    private final String packageName;

    public Package(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    /* (non-Javadoc)
     * @see org.codehaus.typle.src.SourceArtefact#write(java.io.Writer)
     */
    public void write(PrintWriter writer) throws IOException {
        writer.println("package " + packageName + ";");
        writer.println();
    }
}
