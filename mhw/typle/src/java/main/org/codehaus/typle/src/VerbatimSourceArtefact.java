/*
 * $Id$
 */

package org.codehaus.typle.src;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public abstract class VerbatimSourceArtefact extends AbstractSourceArtefact {
    private final String source;

    public VerbatimSourceArtefact(String source) {
        this.source = source;
    }

    public void write(PrintWriter writer) throws IOException {
        writer.print(source);
    }
}
