/*
 * $Id$
 */

package org.codehaus.tagalog.sax;

import java.io.IOException;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import org.codehaus.tagalog.AbstractParser;
import org.codehaus.tagalog.ParserConfiguration;
import org.codehaus.tagalog.TagalogParseException;

/**
 * TagalogSAXParser
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
final class TagalogSAXParser extends AbstractParser implements ContentHandler {
    private SAXParser saxParser;

    private InputSource inputSource;

    TagalogSAXParser(ParserConfiguration configuration, SAXParser saxParser,
                     InputSource inputSource) {
        super(configuration);
        if (saxParser == null)
            throw new NullPointerException("sax parser is null");
        if (inputSource == null)
            throw new NullPointerException("input source is null");
        this.saxParser = saxParser;
        this.inputSource = inputSource;
    }

    protected void doParse() throws TagalogParseException {
        try {
            XMLReader xmlReader;

            xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(this);
            xmlReader.parse(inputSource);
        } catch (SAXException e) {
            Exception cause = e.getException();
            if (cause != null && cause instanceof TagalogParseException)
                throw (TagalogParseException) cause;
            else
                throw new TagalogParseException(e);
        } catch (IOException e) {
            throw new TagalogParseException(e);
        }
    }
    
    public void startElement(String namespaceUri, String localName,
                             String qName, Attributes atts)
        throws SAXException
    {
        try {
            startElement(namespaceUri, localName, new SAXAttributes(atts));
        } catch (TagalogParseException e) {
            throw new SAXException(e);
        }
    }

    public void characters(char[] ch, int start, int length)
        throws SAXException
    {
        try {
            text(ch, start, length);
        } catch (TagalogParseException e) {
            throw new SAXException(e);
        }
    }

    public void endElement(String namespaceUri, String localName, String qName)
        throws SAXException
    {
        try {
            endElement(namespaceUri, localName);
        } catch (TagalogParseException e) {
            throw new SAXException(e);
        }
    }

    public void startDocument() throws SAXException {
    }

    public void endDocument() throws SAXException {
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException {
    }

    public void endPrefixMapping(String prefix) throws SAXException {
    }

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
    }

    public void skippedEntity(String name) throws SAXException {
    }

    public void setDocumentLocator(Locator locator) {
    }

    public void processingInstruction(String target, String data) throws SAXException {
    }
}
