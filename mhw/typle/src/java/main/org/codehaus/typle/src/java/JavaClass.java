/*
 * $Id$
 */

package org.codehaus.typle.src.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.codehaus.typle.src.AbstractSourceContainer;
import org.codehaus.typle.src.SourceArtefact;
import org.codehaus.typle.src.SourceContainer;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class JavaClass
    extends AbstractSourceContainer
    implements SourceContainer
{
    private final Modifier accessLevel;

    private final String className;

    /**
     * Create a Java class that is publicly accessible. In other words,
     * the {@link #getAccessLevel()} method will always return
     * {@link Modifier#PUBLIC}.
     *
     * @param className Name of the class to create, without package prefix.
     */
    public JavaClass(String className) {
        this(Modifier.PUBLIC, className);
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
        super(JavaSource.COMPARATOR);
        checkAccessLevel(accessLevel);
        this.accessLevel = accessLevel;
        this.className = className;
    }

    private void checkAccessLevel(Modifier accessLevel) {
        if (accessLevel != Modifier.PUBLIC
            && accessLevel != Modifier.PROTECTED
            && accessLevel != Modifier.DEFAULT
            && accessLevel != Modifier.PRIVATE)
        {
            throw new IllegalArgumentException("illegal class access level "
                                               + accessLevel);
        }
    }

    public Modifier getAccessLevel() {
        return accessLevel;
    }

    public String getClassName() {
        return className;
    }

    /* (non-Javadoc)
     * @see org.codehaus.typle.src.SourceArtefact#write(java.io.PrintWriter)
     */
    public void write(PrintWriter writer) throws IOException {
        String s;

        writer.println();
        if (accessLevel == Modifier.DEFAULT)
            s = "";
        else
            s = accessLevel + " ";
        writer.println(s + "class " + className + " {");
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
