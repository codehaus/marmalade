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

    /**
     * Subclass of <code>SAXException<code> that we use to propogate a
     * {@link TagalogParseException} through the SAX parser.
     */
    private static final class SAXExceptionWrappingTagalogParseException
        extends SAXException
    {
        SAXExceptionWrappingTagalogParseException(TagalogParseException e) {
            super(e);
        }
    }

    protected void doParse() throws TagalogParseException {
        try {
            XMLReader xmlReader;

            xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(this);
            xmlReader.parse(inputSource);
        } catch (SAXExceptionWrappingTagalogParseException e) {
            throw (TagalogParseException) e.getException();
        } catch (SAXException e) {
            throw new TagalogParseException(e);
        } catch (IOException e) {
            throw new TagalogParseException(e);
        }
    }

    /*
     * JDK 1.4's Crimson SAX parser swallows the exception detail
     * from RuntimeExceptions thrown by the ContentHandler methods.
     * As these errors are likely to be caused by Tag implementations
     * we'd like to preserve the exception with its stack trace, so
     * in the following methods we wrap thrown RuntimeExceptions in
     * a TagalogParseException so they propogate out to the top level.
     */
    
    public void startElement(String namespaceUri, String localName,
                             String qName, Attributes atts)
        throws SAXException
    {
        try {
            startElement(namespaceUri, localName);
        } catch (TagalogParseException e) {
            throw new SAXExceptionWrappingTagalogParseException(e);
        } catch (RuntimeException e) {
            throw new SAXExceptionWrappingTagalogParseException(
                                                new TagalogParseException(e));
        }
    }

    public void characters(char[] ch, int start, int length)
        throws SAXException
    {
        try {
            text(ch, start, length);
        } catch (TagalogParseException e) {
            throw new SAXExceptionWrappingTagalogParseException(e);
        } catch (RuntimeException e) {
            throw new SAXExceptionWrappingTagalogParseException(
                    new TagalogParseException(e));
        }
    }

    public void endElement(String namespaceUri, String localName, String qName)
        throws SAXException
    {
        try {
            endElement(namespaceUri, localName);
        } catch (TagalogParseException e) {
            throw new SAXExceptionWrappingTagalogParseException(e);
        } catch (RuntimeException e) {
            throw new SAXExceptionWrappingTagalogParseException(
                    new TagalogParseException(e));
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
