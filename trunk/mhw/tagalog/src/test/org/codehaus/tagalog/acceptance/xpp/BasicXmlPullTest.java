/*
 * $Id$
 */

package org.codehaus.tagalog.acceptance.xpp;

import java.io.InputStream;
import java.net.URL;

import junit.framework.TestCase;

import org.codehaus.tagalog.ParserConfiguration;
import org.codehaus.tagalog.TagalogParser;
import org.codehaus.tagalog.acceptance.AbstractParserTest;
import org.codehaus.tagalog.acceptance.people.People;
import org.codehaus.tagalog.acceptance.people.PeopleTagLibrary;
import org.codehaus.tagalog.xpp.TagalogXmlPullParserFactory;

/**
 * A basic test of the XPP3 driver for Tagalog, intended as much as a
 * demonstration of the API.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class BasicXmlPullTest extends TestCase {
    public void testParse() throws Exception {
        ParserConfiguration config;
        TagalogXmlPullParserFactory factory;
        URL url;
        InputStream stream;
        TagalogParser parser;
        People people;

        config = new ParserConfiguration();
        config.addTagLibrary(PeopleTagLibrary.NS_URI,
                                          new PeopleTagLibrary());
        config.setDefaultNamespace("tagalog:people");
        factory = new TagalogXmlPullParserFactory(config);

        url = AbstractParserTest.class.getResource("people-no-ns.xml");
        stream = url.openStream();
        parser = factory.createParser(stream);
        people = (People) parser.parse();
        assertEquals(2, people.getPeople().size());
    }
}
