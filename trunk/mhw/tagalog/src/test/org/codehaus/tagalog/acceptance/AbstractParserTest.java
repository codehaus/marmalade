/*
 * $Id$
 */

package org.codehaus.tagalog.acceptance;

import java.net.URL;
import java.util.List;

import org.xml.sax.SAXParseException;

import org.codehaus.tagalog.ParserConfiguration;
import org.codehaus.tagalog.TagalogParseException;
import org.codehaus.tagalog.TagalogParser;
import org.codehaus.tagalog.acceptance.people.People;
import org.codehaus.tagalog.acceptance.people.PeopleTagLibrary;
import org.codehaus.tagalog.acceptance.people.Person;

import junit.framework.TestCase;

/**
 * Abstract base class providing XML parsing tests. Subclasses are responsible
 * for connecting these tests to a concrete parser instance.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public abstract class AbstractParserTest extends TestCase {
    protected abstract TagalogParser createParser(URL testSource,
                                        ParserConfiguration configuration)
        throws Exception;

    private ParserConfiguration peopleConfiguration;

    protected void setUp() throws Exception {
        super.setUp();

        peopleConfiguration = new ParserConfiguration();
        peopleConfiguration.addTagLibrary(PeopleTagLibrary.NS_URI,
                                          new PeopleTagLibrary());
    }

    private void checkPeople(People people) {
        List personList = people.getPeople();
        assertEquals(2, personList.size());
        Person person = (Person) personList.get(0);
        assertEquals("Mark", person.getFirstName());
    }

    public void testParsePeople() throws Exception {
        URL peopleXml = AbstractParserTest.class.getResource("people.xml");
        TagalogParser p = createParser(peopleXml, peopleConfiguration);
        People people = (People) p.parse();
        checkPeople(people);
    }

    public void testParsePeopleNoNamespace() throws Exception {
        URL peopleXml = AbstractParserTest.class.getResource("people-no-ns.xml");
        peopleConfiguration.setDefaultNamespace("tagalog:people");
        TagalogParser p = createParser(peopleXml, peopleConfiguration);
        People people = (People) p.parse();
        checkPeople(people);
    }

    /*
     * Parsing a file with a namespace we don't have a tag library for
     * should fail.
     */
    public void testParsePeopleBadNamespace() throws Exception {
        URL peopleXml = AbstractParserTest.class.getResource("people-bad-ns.xml");
        TagalogParser p = createParser(peopleXml, peopleConfiguration);

        try {
            p.parse();
            fail("should have thrown TagalogParseException");
        } catch (TagalogParseException e) {
            assertNull(e.getCause());
        }
    }

    /*
     * Parsing a file with a tag that we don't recognise should fail.
     */
    public void testParsePeopleBadTag() throws Exception {
        URL peopleXml = AbstractParserTest.class.getResource("people-bad-tag.xml");
        TagalogParser p = createParser(peopleXml, peopleConfiguration);

        try {
            p.parse();
            fail("should have thrown TagalogParseException");
        } catch (TagalogParseException e) {
            assertNull(e.getCause());
        }
    }

    /*
     * Tags might throw exceptions if they have bugs in them; we should
     * preserve all the exception detail.
     */
    public void testParsePeopleBrokenTag() throws Exception {
        URL peopleXml = AbstractParserTest.class.getResource("people-broken-tag.xml");
        TagalogParser p = createParser(peopleXml, peopleConfiguration);

        try {
            p.parse();
            fail("should have thrown NullPointerException");
        } catch (TagalogParseException e) {
            Throwable cause1 = e.getCause();
            assertTrue(cause1 instanceof SAXParseException);
            Exception cause2 = ((SAXParseException) cause1).getException();
            assertTrue(cause2 instanceof NullPointerException);
            assertEquals("from BrokenTag", cause2.getMessage());
        } catch (NullPointerException e) {
            assertEquals("from BrokenTag", e.getMessage());
        }
    }
}
