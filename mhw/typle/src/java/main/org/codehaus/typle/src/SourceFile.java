/*
 * $Id$
 */

package org.codehaus.typle.src;

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class SourceFile extends AbstractSourceContainer {    
    public SourceFile(Comparator artefactOrder) {
        super(artefactOrder);
    }

    public void write(SourceFileWriter writer) throws IOException {
        Iterator iter = iterator();
        while (iter.hasNext()) {
            SourceArtefact artefact = (SourceArtefact) iter.next();
            artefact.write(writer);
        }
    }
}
