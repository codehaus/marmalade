/*
 * $Id$
 */

package org.codehaus.tagalog.acceptance.people;

import org.codehaus.tagalog.AbstractTag;
import org.codehaus.tagalog.Attributes;

/**
 * PeopleTag
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class PeopleTag extends AbstractTag {
    private People people;

    public void begin(String elementName, Attributes attributes) {
        people = new People();
    }

    public void child(Object child) {
        Person person = (Person) child;
        people.addPerson(person);
    }

    public Object end(String elementName) {
        return people;
    }

    public boolean recycle() {
        people = null;
        return true;
    }
}
