/*
 * $Id$
 */

package org.codehaus.typle.src;

import java.util.Comparator;

/**
 * Simple comparator that sorts based on package name.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class SimpleSourceOrdering implements Comparator {
    public static final SimpleSourceOrdering COMPARATOR
                                                = new SimpleSourceOrdering();

    // ensure use of singleton
    private SimpleSourceOrdering() {
    }

    public int compare(Object o1, Object o2) {
        String o1Package = o1.getClass().getPackage().getName();
        String o2Package = o2.getClass().getPackage().getName();
        return o1Package.compareTo(o2Package);
    }
}
