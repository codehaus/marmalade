/*
 * $Id$
 */

package org.codehaus.tagalog.xpp;

import org.xmlpull.v1.XmlPullParser;

import org.codehaus.tagalog.Attributes;

/**
 * A simple wrapper to convert the API of the XML Pull Parser attribute
 * access methods into the Tagalog {@link Attributes} interface.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
class XmlPullAttributes implements Attributes {
    XmlPullParser parser;

    public XmlPullAttributes(XmlPullParser parser) {
        this.parser = parser;
    }

    public int getAttributeCount() {
        return parser.getAttributeCount();
    }

    public String getNamespaceUri(int attributeIndex) {
        return parser.getAttributeNamespace(attributeIndex);
    }

    public String getName(int attributeIndex) {
        return parser.getAttributeName(attributeIndex);
    }

    public String getValue(int attributeIndex) {
        return parser.getAttributeValue(attributeIndex);
    }

    public String getValue(String attributeName) {
        return parser.getAttributeValue("", attributeName);
    }

    public String getValue(String namespaceUri, String attributeName) {
        return parser.getAttributeValue(namespaceUri, attributeName);
    }
}
