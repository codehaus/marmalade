/*
 * $Id$
 */

package org.codehaus.typle.src;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class SourceFile extends AbstractSourceContainer {
    private final PrintWriter output;

    public SourceFile(PrintWriter output, Comparator artefactOrder) {
        super(artefactOrder);
        this.output = output;
    }

    public void writeOutput() throws IOException {
        write(output);
    }

    public void write(PrintWriter writer) throws IOException {
        Iterator iter = iterator();
        while (iter.hasNext()) {
            SourceArtefact artefact = (SourceArtefact) iter.next();
            artefact.write(writer);
        }
    }
}
