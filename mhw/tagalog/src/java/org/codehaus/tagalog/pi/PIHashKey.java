/*
 * $Id$
 */

package org.codehaus.tagalog.pi;

/**
 * A separate class of object to represent XML Processing Instructions in
 * the context <code>Map</code> used to carry state between tags.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public final class PIHashKey {
    private String target;

    /**
     * Construct a <code>PIHashKey</code> for a target.
     *
     * @param target The target of the processing instruction.
     *
     * @throws NullPointerException If target is null.
     */
    public PIHashKey(String target) {
        if (target == null)
            throw new NullPointerException("target is null");
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public boolean equals(Object o) {
        if (!(o instanceof PIHashKey))
            return false;

        PIHashKey other = (PIHashKey) o;
        return other.target.equals(this.target);
    }

    public int hashCode() {
        return target.hashCode();
    }
}
