/*
 * $Id$
 */

package org.codehaus.typle.src.java;

/**
 * Modifiers that can be applied to Java declarations.
 *
 * @see Field
 * @see JavaClass
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class Modifier {
    private String name;

    private Modifier(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public static final Modifier PUBLIC = new Modifier("public");

    public static final Modifier PROTECTED = new Modifier("protected");

    public static final Modifier DEFAULT = new Modifier("");

    public static final Modifier PRIVATE = new Modifier("private");

    public static final Modifier STATIC = new Modifier("static");

    public static final Modifier FINAL = new Modifier("final");
}
