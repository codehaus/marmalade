/*
 * $Id$
 */

package org.codehaus.typle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.typle.util.SAXParserState;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class XmlStoreTypeFactory
    extends AbstractTypeFactory
    implements TypeFactory
{
    private File baseDir;

    public XmlStoreTypeFactory(File baseDir) {
        this.baseDir = baseDir;
    }

    protected boolean fullyQualified(String name) {
        return name.indexOf('.') != -1;
    }

    protected String qualifiedName(String namespace, String name) {
        return namespace + "." + name;
    }

    protected String[] loadTypes(String name) throws TypeLookupException {
        File store = findXmlStore(name);
        if (store == null)
            return new String[0];

        try {
            return parse(store);
        } catch (SAXException e) {
            throw new TypeLookupException("parse failed", e);
        } catch (ParserConfigurationException e) {
            throw new TypeLookupException("SAX configuration error", e);
        } catch (IOException e) {
            throw new TypeLookupException("parse failed", e);
        }
    }

    private File findXmlStore(String name) {
        File store;

        name = packageName(name);
        name = name.replace('.', File.separatorChar) + ".xml";
        for (;;) {
            store = new File(baseDir, name);
            if (store.exists())
                break;
            int slash = name.lastIndexOf(File.separatorChar);
            if (slash == -1) {
                store = null;
                break;
            }
            name = name.substring(0, slash) + "."
                   + name.substring(slash + 1);
        }
        return store;
    }

    private String packageName(String typeName) {
        int dot = typeName.lastIndexOf('.');
        if (dot == -1)
            return "";
        else
            return typeName.substring(0, dot);
    }

    private String[] parse(File store)
        throws SAXException, ParserConfigurationException,
               IOException, TypeLookupException
    {
        XMLReader xmlReader;
        TypeBuilder typeBuilder;

        xmlReader = SAXParserState.createXMLReader();
        typeBuilder = new TypeBuilder();
        xmlReader.setContentHandler(typeBuilder);
        xmlReader.parse(new InputSource(new FileInputStream(store)));
        return typeBuilder.getNewTypes();
    }

    private class TypeBuilder extends DefaultHandler {
        private static final String TYPES = "types";
        private static final String NAMESPACE = "namespace";
        private static final String RECORD = "record";
        private static final String FIELD = "field";
        private static final String NAME = "name";
        private static final String TYPE = "type";

        private SAXParserState state = new SAXParserState();
        private String namespace;
        private LinkedList newTypes = new LinkedList();

        public String[] getNewTypes() {
            return (String[]) newTypes.toArray(new String[newTypes.size()]);
        }

        public void startDocument() throws SAXException {
        }

        public void endDocument() throws SAXException {
            state.reset();
        }

        public void startElement(String uri, String localName, String qName,
            Attributes attributes)
            throws SAXException
        {
            state.startElement(uri, localName, qName);
            if (state.inElement(TYPES)) {
                if (state.containedBy(TYPES, TYPES))
                    throw new SAXException("types element does not nest");
                namespace = attributes.getValue(NAMESPACE);
            } else if (state.inElement(RECORD)) {
                state.openScope();
                state.put(FIELD, new BindingList());
            } else if (state.inElement(FIELD)) {
                state.openScope();
            }
        }

        public void endElement(String uri, String localName, String qName)
            throws SAXException
        {
            if (state.inElement(FIELD, NAME)) {
                state.put(NAME, state.getCharacters());
            } else if (state.inElement(FIELD, TYPE)) {
                state.put(TYPE, new TypePlaceHolder(state.getCharacters()));
            } else if (state.inElement(FIELD)) {
                buildField();
            } else if (state.inElement(RECORD, NAME)) {
                state.put(NAME, state.getCharacters());
            } else if (state.inElement(RECORD)) {
                buildType();
            }
            state.endElement(uri, localName, qName);
        }

        private void buildField() {
            String name;
            Type type;
            Binding binding;
            BindingList fields;

            name = (String) state.get(NAME);
            type = (Type) state.get(TYPE);
            state.closeScope();
            binding = new Binding(name, type);
            fields = (BindingList) state.get(FIELD);
            state.replace(FIELD, fields.add(binding));
        }

        private void buildType() {
            String name;
            BindingList fields;
            RecordType type;

            name = namespace + "." + (String) state.get(NAME);
            fields = (BindingList) state.get(FIELD);
            type = new RecordType(name, fields.toArray());
            addType(name, type);
            newTypes.add(name);
            state.closeScope();
        }

        public void characters(char[] ch, int start, int length)
            throws SAXException
        {
            state.characters(ch, start, length);
        }
    }
}
