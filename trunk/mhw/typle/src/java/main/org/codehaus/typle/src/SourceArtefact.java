/*
 * $Id$
 */

package org.codehaus.typle.src;

import java.io.IOException;

/**
 * Common interface for artefacts that will go into a {@link SourceContainer}.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public interface SourceArtefact {
    void setContainer(SourceContainer container);

    SourceContainer getContainer();

    /**
     * Write the artefact to a {@link SourceFileWriter}.
     *
     * @param writer the <code>SourceFileWriter</code> to write to.
     * @throws IOException if there are problems writing to the
     *                     <code>SourceFileWriter</code>.
     */
    void write(SourceFileWriter writer) throws IOException;
}
