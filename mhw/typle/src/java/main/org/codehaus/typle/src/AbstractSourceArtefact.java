/*
 * $Id$
 */

package org.codehaus.typle.src;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public abstract class AbstractSourceArtefact {
    private SourceContainer container;

    public void setContainer(SourceContainer container) {
        if (this.container == null) {
            this.container = container;
        } else {
            throw new IllegalStateException("source artefact " + this
                                            + " already contained by "
                                            + this.container);
        }
    }

    public SourceContainer getContainer() {
        return container;
    }
}
