/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * AbstractTag
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public abstract class AbstractTag implements Tag {
    public void begin() {
    }

    public Object end() {
        return null;
    }

    public boolean recycle() {
        return true;
    }
}
