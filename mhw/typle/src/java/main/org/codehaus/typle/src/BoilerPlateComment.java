/*
 * $Id$
 */

package org.codehaus.typle.src;

import java.io.IOException;


/**
 * Boilerplate comment to go at the top of the source file.  A BoilerPlate
 * typically consists of multiple lines of text.
 *
 * @author Mark H. Wilkinson, Kristopher Brown
 * @version $Revision$
 */
public final class BoilerPlateComment
    extends AbstractSourceArtefact
    implements SourceArtefact
{
    private String[] lines;
    private final int priority;

    public BoilerPlateComment(String line, int priority) {
        this.lines = new String[] { line };
        this.priority = priority;
    }

    public BoilerPlateComment(String[] lines, int priority) {
        this.lines = lines;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
    
    public void write(SourceFileWriter writer) throws IOException {
        for (int i = 0; i < lines.length; i++) {
            writer.println(lines[i]);
        }
    }
    
    /**
     * Creates a suitable representation, using \n as line sep.
     */
    public String[] getLines() {
        return lines;
    }
}
