/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * <code>TagUtils</code> contains static methods that are of use when
 * writing implementations of the {@link Tag} interface. The signatures
 * and implementations of many of these methods are identical to those
 * in Jelly's <code>TagSupport</code> class.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public final class TagUtils {
    /**
     * Private constructor to prevent instantiation.
     */
    private TagUtils() {
    }

    public static Tag findAncestorWithClass(Tag from, Class tagClass) {
        while (from != null) {
            if (tagClass.isInstance(from))
                return from;
            from = from.getParent();
        }
        return null;
    }
}
