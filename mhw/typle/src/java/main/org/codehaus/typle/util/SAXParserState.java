/*
 * $Id$
 */

package org.codehaus.typle.util;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * SAXParserState encapsulates a stack-based representation of the state
 * of a SAX ContentHandler. It allows the client to enquire whether the
 * current SAX event is occurring within the context of a given set of
 * elements. It also handles collecting character data into a String.
 * Whitespace characters between a pair of element start tags or a pair
 * of element end tags are classed as insignificant and will be ignored.
 * All other character content is significant. Mixed content is not handled:
 * instead, an {@link IllegalStateException} will be thrown when mixed
 * content is detected.
 *
 * <p>
 * Not thoroughly tested with namespace-aware SAX sources. (Or, in
 * otherwords, the unit tests only use the qName argument.)
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class SAXParserState {

    private LinkedList context = new LinkedList();

    private StringBuffer content = null;

    private boolean contentSignificant = false;

    /**
     * Reset the parser state so it can begin parsing a new document.
     */
    public void reset() {
        context.clear();
        content = null;
        contentSignificant = false;
    }

    /**
     * @param namespaceURI
     * @param localName
     * @param qName
     */
    public void startElement(String namespaceURI, String localName,
                             String qName)
    {
        if (contentSignificant) {
            throw new IllegalStateException("mixed content");
        }
        content = null;
        if (context.size() > 0) {
            ((Element) context.getLast()).setElementContent(true);
        }
        context.add(new Element(namespaceURI, localName, qName));
    }

    /**
     * Indicate that an element has ended. Deletes any character content
     * collected within the element - you should call {@link getCharacters}
     * prior to <code>endElement</code> if you need to process the element's
     * character content.
     *
     * @param namespaceURI From SAX API.
     * @param localName From SAX API.
     * @param qName From SAX API.
     */
    public void endElement(String namespaceURI, String localName,
                           String qName)
    {
        Element ending;
        Element expected;

        ending = new Element(namespaceURI, localName, qName);
        expected = (Element) context.getLast();
        if (!ending.equals(expected)) {
            throw new IllegalStateException(
                "end element " + ending + " does not match context " + expected
            );
        }
        context.removeLast();
        content = null;
        contentSignificant = false;
    }

    public boolean inElement(String qName) {
        Element target;

        target = new Element(null, null, qName);
        return target.equals(context.getLast());
    }

    public boolean containedBy(String qName) {
        Element target;
        ListIterator i;
        Element element;

        target = new Element(null, null, qName);
        i = context.listIterator(context.size());
        while (i.hasPrevious()) {
            element = (Element) i.previous();
            if (target.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public boolean containedBy(String qName1, String qName2) {
        Element target;
        ListIterator i;
        Element element;

        target = new Element(null, null, qName2);
        i = context.listIterator(context.size());
        while (i.hasPrevious()) {
            element = (Element) i.previous();
            if (target.equals(element)) {
                break;
            }
        }
        target = new Element(null, null, qName1);
        while (i.hasPrevious()) {
            element = (Element) i.previous();
            if (target.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public void characters(char[] ch, int start, int length) {
        if (content == null) {
            if (context.size() == 0) {
                throw new IllegalStateException("characters outside element");
            }
            if (((Element) context.getLast()).isElementContent()) {
                if (!charactersSignificant(ch, start, length)) {
                    return;
                }
                throw new IllegalStateException("mixed content");
            }
            content = new StringBuffer();
            contentSignificant = false;
        }
        content.append(ch, start, length);
        if (!contentSignificant && charactersSignificant(ch, start, length)) {
            contentSignificant = true;
        }
    }

    public String getCharacters() {
        if (content == null) {
            if (context.size() == 0) {
                throw new IllegalStateException(
                    "attempt to get characters outside element"
                );
            }
            if (((Element) context.getLast()).isElementContent()) {
                throw new IllegalStateException("mixed content");
            }
            return "";
        }
        return content.toString();
    }

    private boolean charactersSignificant(char[] ch, int start, int length) {
        for (int i = start; i < length; i++) {
            if (!Character.isWhitespace(ch[i])) {
                return true;
            }
        }
        return false;
    }

    private static final class Element {
        private final String namespaceURI;
        private final String localName;
        private final String qName;
        private boolean elementContent;

        public Element(String namespaceURI, String localName, String qName) {
            this.namespaceURI = namespaceURI;
            this.localName = localName;
            this.qName = qName;
            this.elementContent = false;
        }

        public String getLocalName() {
            return localName;
        }

        public String getNamespaceURI() {
            return namespaceURI;
        }

        public String getQName() {
            return qName;
        }

        public boolean isElementContent() {
            return elementContent;
        }

        public void setElementContent(boolean b) {
            elementContent = b;
        }

        public boolean equals(Object o) {
            Element other;

            if (!(o instanceof Element)) {
                return false;
            }
            other = (Element) o;
            return (qName == null)? other.qName == null
                                  : qName.equals(other.qName);
        }

        public int hashCode() {
            return qName.hashCode();
        }

        public String toString() {
            return "<" + qName + ">";
        }
    }
}
