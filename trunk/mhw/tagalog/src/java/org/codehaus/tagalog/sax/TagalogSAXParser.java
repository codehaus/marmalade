/*
 * $Id$
 */

package org.codehaus.tagalog.sax;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import org.codehaus.tagalog.AbstractParser;
import org.codehaus.tagalog.ParserConfiguration;

/**
 * TagalogSAXParser
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
final class TagalogSAXParser extends AbstractParser implements ContentHandler {
    private SAXParser saxParser;

    private InputSource inputSource;

    TagalogSAXParser(ParserConfiguration configuration, SAXParser saxParser, InputSource inputSource) {
        super(configuration);
        if (saxParser == null)
            throw new NullPointerException("sax parser is null");
        if (inputSource == null)
            throw new NullPointerException("input source is null");
        this.saxParser = saxParser;
        this.inputSource = inputSource;
    }

    public void parse() throws Exception {
        XMLReader xmlReader;

        xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(this);
        xmlReader.parse(inputSource);
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    public void endDocument() throws SAXException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    public void startDocument() throws SAXException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
     */
    public void endPrefixMapping(String prefix) throws SAXException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
     */
    public void skippedEntity(String name) throws SAXException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    public void setDocumentLocator(Locator locator) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
     */
    public void processingInstruction(String target, String data) throws SAXException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
     */
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        // TODO Auto-generated method stub
        
    }

}
