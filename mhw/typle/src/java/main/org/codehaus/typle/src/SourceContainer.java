/*
 * $Id$
 */

package org.codehaus.typle.src;

/**
 * A container for {@link SourceArtefact}s.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public interface SourceContainer extends SourceArtefact {
    /**
     * Add a source artefact to this container, or replace the existing one
     * if one exists.
     *
     * @param artefact The artefact to add.
     */
    void add(SourceArtefact artefact);
}
