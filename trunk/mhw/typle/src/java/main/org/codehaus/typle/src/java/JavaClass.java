/*
 * $Id$
 */

package org.codehaus.typle.src.java;

import java.io.IOException;
import java.util.Iterator;

import org.codehaus.typle.src.AbstractSourceContainer;
import org.codehaus.typle.src.SourceArtefact;
import org.codehaus.typle.src.SourceContainer;
import org.codehaus.typle.src.SourceFileWriter;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class JavaClass
    extends AbstractSourceContainer
    implements SourceContainer
{
    private final Modifier[] modifiers;

    private final String className;

    private static final Modifier[] DEFAULT_MODIFIERS = new Modifier[] {
        Modifier.PUBLIC, Modifier.FINAL,
    };

    /**
     * Create a Java class that is publicly accessible. In other words,
     * the {@link #getAccessLevel()} method will always return
     * {@link Modifier#PUBLIC}.
     *
     * @param className Name of the class to create, without package prefix.
     */
    public JavaClass(String className) {
        this(DEFAULT_MODIFIERS, className);
    }

    /**
     * Create a Java class.
     *
     * @param accessLevel Access level of the class: one of
     *                    {@link Modifier#PUBLIC},
     *                    {@link Modifier#PROTECTED},
     *                    {@link Modifier#DEFAULT} or
     *                    {@link Modifier#PRIVATE}.
     * @param className Name of the class to create, without package prefix.
     */
    public JavaClass(Modifier accessLevel, String className) {
        this(new Modifier[] { accessLevel }, className);
    }

    public JavaClass(Modifier[] modifiers, String className) {
        super(JavaSource.COMPARATOR);
        this.modifiers = modifiers;
        this.className = className;

        // check for well-formed modifier list
        Modifier accessibility = Modifier.accessibility(modifiers);
    }

    public Modifier getAccessLevel() {
        return Modifier.accessibility(modifiers);
    }

    public String getClassName() {
        return className;
    }

    public void write(SourceFileWriter writer) throws IOException {
        String mods = Modifier.toString(modifiers);
        String space = (mods.length() == 0)? "" : " ";

        writer.println();
        writer.println(mods + space + "class " + className + " {");
        Iterator iter = iterator();
        while (iter.hasNext()) {
            SourceArtefact artefact = (SourceArtefact) iter.next();
            artefact.write(writer);
            if (iter.hasNext())
                writer.println();
        }
        writer.println("}");
    }
}
