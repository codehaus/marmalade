/*
 * $Id$
 */

package org.codehaus.tagalog.acceptance;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.xml.sax.SAXParseException;

import org.codehaus.tagalog.ParseError;
import org.codehaus.tagalog.ParserConfiguration;
import org.codehaus.tagalog.TagalogParseException;
import org.codehaus.tagalog.TagalogParser;
import org.codehaus.tagalog.acceptance.people.People;
import org.codehaus.tagalog.acceptance.people.PeopleTagLibrary;
import org.codehaus.tagalog.acceptance.people.Person;
import org.codehaus.tagalog.pi.RecordMostRecentPIHandler;
import org.codehaus.tagalog.pi.RecordAllPIHandler;
import org.codehaus.tagalog.pi.PIHashKey;

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
     * should return an error.
     */
    public void testParsePeopleBadNamespace() throws Exception {
        URL peopleXml = AbstractParserTest.class.getResource("people-bad-ns.xml");
        TagalogParser p = createParser(peopleXml, peopleConfiguration);
        People people = (People) p.parse();
        assertNull(people);
        ParseError[] errors = p.parseErrors();
        assertEquals(1, errors.length);
        assertEquals(3, errors[0].getLocation().getLine());
        assertEquals("no tag library for namespace 'bogus:namespace'",
                     errors[0].getMessage());
    }

    /*
     * Parsing a file with a tag that we don't recognise should return an
     * error, but the content should still be returned ok.
     */
    public void testParsePeopleBadTag() throws Exception {
        URL peopleXml = AbstractParserTest.class.getResource("people-bad-tag.xml");
        TagalogParser p = createParser(peopleXml, peopleConfiguration);
        People people = (People) p.parse();
        assertNotNull(people);
        ParseError[] errors = p.parseErrors();
        assertEquals(1, errors.length);
        assertEquals(8, errors[0].getLocation().getLine());
        assertEquals("no tag 'username' in tag library for namespace 'tagalog:people'",
                     errors[0].getMessage());
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

    private void checkPeopleEntities(People people) {
        List personList = people.getPeople();
        assertEquals(4, personList.size());
        Person person = (Person) personList.get(0);
        assertEquals("ampersand", person.getUserId());
        assertEquals("name with & & ampersands", person.getFirstName());
        assertEquals("name with & & ampersands", person.getLastName());
        person = (Person) personList.get(1);
        assertEquals("angles", person.getUserId());
        assertEquals("name with < > angles", person.getFirstName());
        assertEquals("name with < > angles", person.getLastName());
        person = (Person) personList.get(2);
        assertEquals("quotes", person.getUserId());
        assertEquals("name with ' \" quotes", person.getFirstName());
        assertEquals("name with ' \" quotes", person.getLastName());
        person = (Person) personList.get(3);
        assertEquals("cdata", person.getUserId());
        assertEquals("name with <cdata>&<section> cdata", person.getFirstName());
        assertEquals("name with <cdata>&<section> cdata", person.getLastName());
    }

    /*
     * Parsers might convert the different XML quoting mechanisms into
     * separate events delivered to the tags. Make sure the content gets
     * through.
     */
    public void testParsePeopleQuoting() throws Exception {
        URL peopleXml = AbstractParserTest.class.getResource("people-quoting.xml");
        TagalogParser p = createParser(peopleXml, peopleConfiguration);
        People people = (People) p.parse();
        checkPeopleEntities(people);
    }

    /*
     * The XmlPullParser uses a different event-delivery mecahnism when
     * processing instructions are being processed. Check this case too.
     */
    public void testParsePeopleQuotingWithPIHandler() throws Exception {
        peopleConfiguration.setProcessingInstructionHandler(
                                            RecordMostRecentPIHandler.INSTANCE);

        URL peopleXml = AbstractParserTest.class.getResource("people-quoting.xml");
        TagalogParser p = createParser(peopleXml, peopleConfiguration);
        People people = (People) p.parse();
        checkPeopleEntities(people);
    }

    /*
     * XML documents can contain processing instructions. Check that
     * they are ignored normally.
     */
    public void testParsePeopleWithNoProcessingInstructionHandler()
        throws Exception
    {
        URL peopleXml = AbstractParserTest.class.getResource("people-pi1.xml");
        TagalogParser p = createParser(peopleXml, peopleConfiguration);
        HashMap map = new HashMap();
        People people = (People) p.parse(map);
        assertNotNull(people);

        assertNull(map.get(new PIHashKey("foo-pi")));
        assertNull(map.get(new PIHashKey("bar-pi")));
    }

    /*
     * Check that processing instructions are handed off to
     * {@link RecordMostRecentPIHandler} properly.
     */
    public void testParsePeopleWithNormalProcessingInstructionHandler()
        throws Exception
    {
        peopleConfiguration.setProcessingInstructionHandler(
                                            RecordMostRecentPIHandler.INSTANCE);

        URL peopleXml = AbstractParserTest.class.getResource("people-pi1.xml");
        TagalogParser p = createParser(peopleXml, peopleConfiguration);
        HashMap map = new HashMap();
        People people = (People) p.parse(map);
        assertNotNull(people);

        String data = (String) map.get(new PIHashKey("foo-pi"));
        assertEquals("second value", data);
        data = (String) map.get(new PIHashKey("bar-pi"));
        assertEquals("hello", data);
    }

    /*
     * Check that processing instructions handed off to
     * {@link RecordAllPIHandler} are processed correctly.
     */
    public void testParsePeopleWithProcessingInstructionMapOfListsHandler()
        throws Exception
    {
        peopleConfiguration.setProcessingInstructionHandler(
                                            RecordAllPIHandler.INSTANCE);

        URL peopleXml = AbstractParserTest.class.getResource("people-pi1.xml");
        TagalogParser p = createParser(peopleXml, peopleConfiguration);
        HashMap map = new HashMap();
        People people = (People) p.parse(map);
        assertNotNull(people);

        List piList = (List) map.get(new PIHashKey("foo-pi"));
        assertEquals(2, piList.size());
        assertEquals("value bar", piList.get(0));
        assertEquals("second value", piList.get(1));
        piList = (List) map.get(new PIHashKey("bar-pi"));
        assertEquals(1, piList.size());
        assertEquals("hello", piList.get(0));
    }
}
