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
public final class Import
    extends AbstractSourceArtefact
    implements SourceArtefact
{
    private final String importSpecification;

    public Import(String packageName) {
        this.importSpecification = packageName;
    }

    /**
     * @return The import specification.
     */
    public String getImportSpecification() {
        return importSpecification;
    }

    /* (non-Javadoc)
     * @see org.codehaus.typle.src.SourceArtefact#write(java.io.Writer)
     */
    public void write(PrintWriter writer) throws IOException {
        if (!(importSpecification.startsWith("java.lang."))) {
            writer.println("import " + importSpecification + ";");
        }
    }
}
