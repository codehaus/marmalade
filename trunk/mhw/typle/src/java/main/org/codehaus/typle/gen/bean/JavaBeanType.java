/*
 * $Id$
 */

package org.codehaus.typle.gen.bean;

/**
 * Typesafe-enumeration representing the different kinds of JavaBean that
 * can be generated.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class JavaBeanType {
    private final String name;

    private JavaBeanType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public static final JavaBeanType INTERFACE
        = new JavaBeanType("interface");

    public static final JavaBeanType CLASS
        = new JavaBeanType("class");

    public static final JavaBeanType ABSTRACT_CLASS
        = new JavaBeanType("abstract class");
}
