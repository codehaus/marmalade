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
public final class Import
    extends AbstractSourceArtefact
    implements SourceArtefact
{
    private final String importSpecification;

    public Import(String importSpecification) {
        this.importSpecification = importSpecification;
    }

    /**
     * @return The import specification.
     */
    public String getImportSpecification() {
        return importSpecification;
    }

    public void write(SourceFileWriter writer) throws IOException {
        if (!(importSpecification.startsWith("java.lang."))) {
            writer.println("import " + importSpecification + ";");
        }
    }
}
