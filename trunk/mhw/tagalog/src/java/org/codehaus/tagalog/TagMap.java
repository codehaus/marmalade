/*
 * $Id$
 */

package org.codehaus.tagalog;

import java.util.Map;

/**
 * A simple map from tag names to classes that implement the
 * {@link TagLibrary} interface.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public final class TagMap {
    private Map tagMap = new java.util.TreeMap();

    /**
     * Associate a {@link Tag} implementation class with a tag name.
     *
     * @param tag The tag name.
     * @param tagClass The providing the <code>Tag</code> implemtation.
     *
     * @throws IllegalArgumentException if the map already contains an
     *     entry for the supplied tag name, or <code>tagClass</code> does
     *     not implement the <code>Tag</code> interface.
     * @throws NullPointerException if either argument is null.
     */
    public void registerTag(String tag, Class tagClass) {
        if (tagMap.containsKey(tag))
            throw new IllegalArgumentException("duplicate tag '" + tag + "'");
        if (tagClass == null)
            throw new NullPointerException("tagClass is null");
        if (!(Tag.class.isAssignableFrom(tagClass)))
            throw new IllegalArgumentException(tagClass + " does not implement the Tag interface");
        tagMap.put(tag, tagClass);
    }

    /**
     * Retrieve the {@link Tag} implementation for the supplied tag name.
     *
     * @param tag The tag name to look up.
     * @return The class of the <code>Tag</code> implementation,
     *     or <code>null</code> if the map has no entry for the tag name.
     * @throws NullPointerException if tag is <code>null</code>.
     */
    public Class lookupTag(String tag) {
        return (Class) tagMap.get(tag);
    }
}
