/*
 * $Id$
 */

package org.codehaus.typle.src;

/**
 * Boilerplate comment to go at the top of the source file.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class BoilerPlateComment
    extends VerbatimSourceArtefact
    implements SourceArtefact
{
    private final int priority;

    public BoilerPlateComment(String source, int priority) {
        super(source);
        this.priority = priority;
    }

    public Object getCommentSource() {
        return super.getSource();
    }

    public int getPriority() {
        return priority;
    }
}
