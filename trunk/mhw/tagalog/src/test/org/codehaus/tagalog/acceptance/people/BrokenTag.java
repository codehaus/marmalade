/*
 * $Id$
 */

package org.codehaus.tagalog.acceptance.people;

import org.codehaus.tagalog.AbstractTag;
import org.codehaus.tagalog.Attributes;

/**
 * A tag that throws a <code>NullPointerException</code> when the
 * begin method is called, simulating a bug in a tag. This allows us
 * to make sure that exception detail is preserved.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public final class BrokenTag extends AbstractTag {
    public void begin(String elementName, Attributes attributes) {
        throw new NullPointerException("from BrokenTag");
    }
}
