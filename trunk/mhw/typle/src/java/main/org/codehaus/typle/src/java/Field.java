/*
 * $Id$
 */

package org.codehaus.typle.src.java;

import java.io.IOException;
import java.io.PrintWriter;

import org.codehaus.typle.src.AbstractSourceArtefact;
import org.codehaus.typle.src.SourceArtefact;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class Field
    extends AbstractSourceArtefact
    implements SourceArtefact
{
    private final String type;

    private final String fieldName;

    public Field(String type, String fieldName) {
        this.type = type;
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    /* (non-Javadoc)
     * @see org.codehaus.typle.src.SourceArtefact#write(java.io.Writer)
     */
    public void write(PrintWriter writer) throws IOException {
        writer.println("private " + type + " " + fieldName + ";");
    }
}
