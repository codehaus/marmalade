/*
 * $Id$
 */

package org.codehaus.tagalog.acceptance.people;

import org.codehaus.tagalog.AbstractTag;

/**
 * PersonTag
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public final class PersonTag extends AbstractTag {
    Person person;

    public void begin(String elementName) {
        person = new Person();
    }

    public Object end(String elementName) {
        return person;
    }

    public boolean recycle() {
        person = null;
        return true;
    }
}
