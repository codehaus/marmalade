/*
 * $Id$
 */

package org.codehaus.tagalog.xpp;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import org.codehaus.tagalog.AbstractParser;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.ParserConfiguration;
import org.codehaus.tagalog.TagalogParseException;

/**
 * TagalogXmlPullParser
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
final class TagalogXmlPullParser extends AbstractParser {
    private XmlPullParser xpp;

    private XmlPullSource source;

    TagalogXmlPullParser(ParserConfiguration configuration,
                         XmlPullParser xpp, XmlPullSource source)
    {
        super(configuration);
        if (xpp == null)
            throw new NullPointerException("xml pull parser is null");
        if (source == null)
            throw new NullPointerException("input source is null");
        this.xpp = xpp;
        this.source = source;
    }

    protected void doParse() throws TagalogParseException {
        try {
            internalDoParse();
        } catch (XmlPullParserException e) {
            throw new TagalogParseException(e);
        } catch (IOException e) {
            throw new TagalogParseException(e);
        }
    }

    private void internalDoParse()
        throws TagalogParseException, XmlPullParserException, IOException
    {
        Attributes attributes = new XmlPullAttributes(xpp);
        boolean handlingProcessingInstructions;
        int eventType;
        int[] characterOffsets = new int[2];

        handlingProcessingInstructions = handlingProcessingInstructions();
        source.setInputFor(xpp);
        eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                startElement(xpp.getNamespace(), xpp.getName(), attributes);
            } else if (eventType == XmlPullParser.TEXT
                       || eventType == XmlPullParser.CDSECT) {
                char[] chars = xpp.getTextCharacters(characterOffsets);
                text(chars, characterOffsets[0], characterOffsets[1]);
            } else if (eventType == XmlPullParser.END_TAG) {
                endElement(xpp.getNamespace(), xpp.getName());
            } else if (eventType == XmlPullParser.ENTITY_REF) {
                String entity = xpp.getText();
                text(entity.toCharArray(), 0, entity.length());
            } else if (eventType == XmlPullParser.PROCESSING_INSTRUCTION) {
                String instruction = xpp.getText();
                int space = instruction.indexOf(' ');
                if (space != -1) {
                    processingInstruction(instruction.substring(0, space),
                                          instruction.substring(space + 1));
                } else {
                    processingInstruction(instruction, "");
                }
            }
            if (handlingProcessingInstructions)
                eventType = xpp.nextToken();
            else
                eventType = xpp.next();
        }
    }

    protected int getErrorLineNumber() {
        return xpp.getLineNumber();
    }
}
