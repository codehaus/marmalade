/*
 * $Id$
 */

package org.codehaus.tagalog.sax;

import org.codehaus.tagalog.Attributes;

/**
 * A simple wrapper to convert the API of the SAX <code>Attributes</code>
 * interface into the Tagalog {@link Attributes} interface.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
class SAXAttributes implements Attributes {
    org.xml.sax.Attributes saxAttributes;

    public SAXAttributes(org.xml.sax.Attributes saxAttributes) {
        this.saxAttributes = saxAttributes;
    }

    public int getAttributeCount() {
        return saxAttributes.getLength();
    }

    public String getNamespaceUri(int attributeIndex) {
        return saxAttributes.getURI(attributeIndex);
    }

    public String getName(int attributeIndex) {
        return saxAttributes.getLocalName(attributeIndex);
    }

    public String getValue(int attributeIndex) {
        return saxAttributes.getValue(attributeIndex);
    }

    public String getValue(String attributeName) {
        return saxAttributes.getValue("", attributeName);
    }

    public String getValue(String namespaceUri, String attributeName) {
        return saxAttributes.getValue(namespaceUri, attributeName);
    }
}
