/*
 * $Id$
 */

package org.codehaus.typle.src.java;

import java.io.IOException;

import org.codehaus.typle.src.AbstractSourceArtefact;
import org.codehaus.typle.src.SourceArtefact;
import org.codehaus.typle.src.SourceFileWriter;

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

    public void write(SourceFileWriter writer) throws IOException {
        String mods = Modifier.toString(modifiers);
        String space = (mods.length() == 0)? "" : " ";
        writer.println(mods + space + type + " " + fieldName + ";");
    }
}
