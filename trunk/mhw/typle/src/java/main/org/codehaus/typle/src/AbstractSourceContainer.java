/*
 * $Id$
 */

package org.codehaus.typle.src;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Abstract superclass for implementations of the {@link SourceContainer}
 * interface. Clients of the class can add {@link SourceArtefact}s to the
 * container, and obtain an iterator that will return the
 * <code>SourceArtefact</code>s in the order specified by the
 * {@link Comparator} provided to the constructor.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public abstract class AbstractSourceContainer
    extends AbstractSourceArtefact
    implements SourceContainer
{
    private TreeSet artefacts;

    public AbstractSourceContainer(Comparator artefactOrder) {
        artefacts = new TreeSet(artefactOrder);
    }

    public void add(SourceArtefact artefact) {
        artefact.setContainer(this);
        artefacts.remove(artefact);
        artefacts.add(artefact);
    }

    public Iterator iterator() {
        return artefacts.iterator();
    }
}
