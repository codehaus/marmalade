/*
 * $Id$
 */

package org.codehaus.typle.src.java;

import java.io.IOException;

import org.codehaus.typle.src.AbstractSourceArtefact;
import org.codehaus.typle.src.SourceArtefact;
import org.codehaus.typle.src.SourceFileWriter;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class JavaPackage
    extends AbstractSourceArtefact
    implements SourceArtefact
{
    private final String packageName;

    public JavaPackage(String packageName) {
        if (packageName == null)
            throw new NullPointerException("package name is null");
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    /* (non-Javadoc)
     * @see org.codehaus.typle.src.SourceArtefact#write(java.io.Writer)
     */
    public void write(SourceFileWriter writer) throws IOException {
        if (!packageName.equals("")) {
            writer.println("package " + packageName + ";");
            writer.println();
        }
    }
}
