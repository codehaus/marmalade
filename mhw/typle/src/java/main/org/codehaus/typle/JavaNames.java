/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class JavaNames {
    // prevent instantiation
    private JavaNames() {
    }

    public static String packageName(String fullyQualified) {
        int dot = fullyQualified.lastIndexOf('.');
        if (dot == -1)
            return "";
        else
            return fullyQualified.substring(0, dot);
    }

    public static String className(String fullyQualified) {
        int dot = fullyQualified.lastIndexOf('.');
        if (dot == -1)
            return fullyQualified;
        else
            return fullyQualified.substring(dot + 1);
    }
}
