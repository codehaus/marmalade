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
    private final Modifier[] modifiers;

    private final String type;

    private final String fieldName;

    private static final Modifier[] DEFAULT_MODIFIERS = new Modifier[] {
        Modifier.PRIVATE,
    };

    public Field(String type, String fieldName) {
        this(DEFAULT_MODIFIERS, type, fieldName);
    }

    public Field(Modifier[] modifiers, String type, String fieldName) {
        this.modifiers = modifiers;
        this.type = type;
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void write(PrintWriter writer) throws IOException {
        writer.println(Modifier.toString(modifiers)
            + " " + type + " " + fieldName + ";");
    }
}
