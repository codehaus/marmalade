/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * Tag
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public interface Tag {
    void begin();
    Object end();
    boolean recycle();
}
