/*
 * $Id$
 */

package org.codehaus.tagalog.acceptance.people;

import org.codehaus.tagalog.AbstractTag;
import org.codehaus.tagalog.TagalogParseException;

/**
 * AttributeTag
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public final class AttributeTag extends AbstractTag {
    private StringBuffer buffer;

    public void begin(String elementName) {
        buffer = new StringBuffer();
    }

    public void text(char[] characters, int start, int length) {
        buffer.append(characters, start, length);
    }

    public Object end(String elementName) throws TagalogParseException {
        PersonTag parent = (PersonTag) requireAncestor("person", PersonTag.class);
        if (elementName.equals("first-name"))
            parent.person.setFirstName(buffer.toString());
        else if (elementName.equals("last-name"))
            parent.person.setLastName(buffer.toString());
        return null;
    }
}
