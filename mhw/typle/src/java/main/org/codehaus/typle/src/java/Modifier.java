/*
 * $Id$
 */

package org.codehaus.typle.src.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Modifiers that can be applied to Java declarations. The modifiers sort
 * into the order suggested by Java Language Specification sections
 * <a href="http://java.sun.com/docs/books/jls/second_edition/html/classes.doc.html#21613">8.1.1</a>,
 * <a href="http://java.sun.com/docs/books/jls/second_edition/html/classes.doc.html#78091">8.3.1</a> and
 * <a href="http://java.sun.com/docs/books/jls/second_edition/html/classes.doc.html#78188">8.4.3</a>.
 *
 * @see JavaClass
 * @see Field
 * @see Method
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class Modifier implements Comparable {
    private String name;

    private static int nextOrdinal = 0;

    private final int ordinal = nextOrdinal++;

    private Modifier(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    /**
     * Convert a list of modifiers into a string. The string contains the
     * name of each modifier, sorted into the order specified by the Java
     * Language Specification, with a single space between each modifier.
     *
     * @param modifiers Array of modifiers to be formatted.
     * @return String containg the modifiers in order separated by spaces.
     */
    public static String toString(Modifier[] modifiers) {
        int l = modifiers.length;
        Modifier[] copy = new Modifier[l];
        StringBuffer buf = new StringBuffer();

        System.arraycopy(modifiers, 0, copy, 0, l);
        Arrays.sort(copy);
        for (int i = 0; i < l; i++) {
            if (i > 0)
                buf.append(' ');
            buf.append(copy[i]);
        }
        return buf.toString();
    }

    public int compareTo(Object o) {
        return ordinal - ((Modifier) o).ordinal;
    }

    public static final Modifier PUBLIC = new Modifier("public");

    public static final Modifier PROTECTED = new Modifier("protected");

    public static final Modifier DEFAULT = new Modifier("");

    public static final Modifier PRIVATE = new Modifier("private");

    public static final Modifier ABSTRACT = new Modifier("abstract");

    public static final Modifier STATIC = new Modifier("static");

    public static final Modifier FINAL = new Modifier("final");

    public static final Modifier TRANSIENT = new Modifier("transient");

    public static final Modifier VOLATILE = new Modifier("volatile");

    public static final Modifier SYNCHRONIZED = new Modifier("synchronized");

    public static final Modifier NATIVE = new Modifier("native");

    public static final Modifier STRICTFP = new Modifier("strictfp");

    private static Collection ACCESSIBILITY_MODIFIERS = Arrays.asList(
        new Modifier[] { PUBLIC, PROTECTED, DEFAULT, PRIVATE }
    );

    public static Modifier accessibility(Modifier[] modifiers) {
        List trimmed = new ArrayList(Arrays.asList(modifiers));
        trimmed.retainAll(ACCESSIBILITY_MODIFIERS);
        if (trimmed.size() == 0)
            return DEFAULT;
        else if (trimmed.size() == 1)
            return (Modifier) trimmed.get(0);
        else
            throw new IllegalArgumentException(
                "more than one accessibility modifier: " + trimmed.toString());
    }
}
