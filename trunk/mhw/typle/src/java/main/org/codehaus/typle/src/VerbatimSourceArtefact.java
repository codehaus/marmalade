/*
 * $Id$
 */

package org.codehaus.typle.src;

import java.io.IOException;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public abstract class VerbatimSourceArtefact extends AbstractSourceArtefact {
    private final String source;

    public VerbatimSourceArtefact(String source) {
        this.source = source;
    }

    protected String getSource() {
        return source;
    }

    public void write(SourceFileWriter writer) throws IOException {
        writer.print(source);
    }
}
