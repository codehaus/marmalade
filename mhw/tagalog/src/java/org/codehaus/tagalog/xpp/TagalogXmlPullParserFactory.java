/*
 * $Id$
 */

package org.codehaus.tagalog.xpp;

import java.io.InputStream;
import java.io.Reader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import org.codehaus.tagalog.ParserConfiguration;
import org.codehaus.tagalog.TagalogParser;

/**
 * TagalogXmlPullParserFactory
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class TagalogXmlPullParserFactory {
    private ParserConfiguration configuration;

    private XmlPullParserFactory factory;

    /**
     * Create a factory for parsers that will use the XML Pull Parser
     * as the low-level XML parser. All parsers created by this factory
     * will share the configuration passed to the constructor. The
     * parser configuration should not be altered after it has been
     * passed to a parser factory.
     *
     * @param configuration The configuration for parsers created by this
     * factory. Configurations should not be shared or altered after they
     * have been used to create a factory.
     */
    public TagalogXmlPullParserFactory(ParserConfiguration configuration) {
        if (configuration == null)
            throw new NullPointerException("configuration is null");
        this.configuration = configuration;
    }

    private synchronized final XmlPullParser getXmlPullParser()
        throws XmlPullParserException
    {
        if (factory == null) {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
        }
        return factory.newPullParser();
    }

    private final TagalogParser createParser(XmlPullSource source)
        throws XmlPullParserException
    {
        return new TagalogXmlPullParser(configuration, getXmlPullParser(),
                                        source);
    }

    public TagalogParser createParser(InputStream byteStream)
        throws XmlPullParserException
    {
        return createParser(new XmlPullSource(byteStream, null));
    }

    public TagalogParser createParser(InputStream byteStream, String encoding)
        throws XmlPullParserException
    {
        return createParser(new XmlPullSource(byteStream, encoding));
    }

    public TagalogParser createParser(Reader characterStream)
        throws XmlPullParserException
    {
        return createParser(new XmlPullSource(characterStream));
    }
}
