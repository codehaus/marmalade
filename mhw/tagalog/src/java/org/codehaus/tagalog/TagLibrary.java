/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * TagLibrary
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public interface TagLibrary {
    Tag getTag(String element);
    void releaseTag(String element, Tag tag);
}
