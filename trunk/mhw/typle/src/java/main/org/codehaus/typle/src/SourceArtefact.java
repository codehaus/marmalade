/*
 * $Id$
 */

package org.codehaus.typle.src;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Common interface for artefacts that will go into a {@link SourcContainer}.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public interface SourceArtefact {
    void setContainer(SourceContainer container);

    SourceContainer getContainer();

    /**
     * Write the artefact to a {@link Writer}.
     *
     * @param writer the <code>Writer</code> to write to.
     * @throws IOException if there are problems writing to the
     *                     <code>Writer</code>.
     */
    void write(PrintWriter writer) throws IOException;
}
