/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * {@link Type} instances to represent the Java primitive data types. 
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class JavaPrimitive extends TypeHelper implements Type {
    /**
     * Create a JavaPrimitive from the VM's class describing the primitive.
     */
    JavaPrimitive(Class clazz) {
        super(clazz.getName());
        if (!clazz.isPrimitive()) {
            throw new IllegalArgumentException(clazz.getName()
                                               + " is not a primitive type");
        }
    }
}
