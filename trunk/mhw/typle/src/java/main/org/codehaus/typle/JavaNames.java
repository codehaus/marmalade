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

    public static String toCamelCase(String string) {
        char[] c = string.toCharArray();
        boolean upperCase = false;
        int s = 0, d = 0;

        while (s < c.length) {
            if (Character.isLetterOrDigit(c[s])) {
                if (upperCase) {
                    c[d++] = Character.toUpperCase(c[s++]);
                    upperCase = false;
                } else {
                    c[d++] = c[s++];
                }
            } else {
                s++;
                upperCase = true;
            }
        }
        return new String(c, 0, d);
    }

    public static String lowerCaseFirstLetter(String string) {
        if (Character.isLowerCase(string.charAt(0)))
            return string;
        char[] c = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

    public static String upperCaseFirstLetter(String string) {
        if (Character.isUpperCase(string.charAt(0)))
            return string;
        char[] c = string.toCharArray();
        c[0] = Character.toUpperCase(c[0]);
        return new String(c);
    }
}
