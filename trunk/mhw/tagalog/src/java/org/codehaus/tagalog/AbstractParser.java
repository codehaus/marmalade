/*
 * $Id$
 */

package org.codehaus.tagalog;

import java.util.Map;

/**
 * AbstractParser
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public abstract class AbstractParser implements TagalogParser {
    private ParserConfiguration configuration;

    private Map context;

    private Object parseResult;

    private Tag currentTag;

    /**
     * Construct an <code>AbstractParser</code> that uses the specified
     * parser configuration.
     *
     * @param resolver The fallback tag library resolver.
     */
    protected AbstractParser(ParserConfiguration configuration) {
        this.configuration = configuration;
    }

    public Object parse() throws TagalogParseException {
        return parse(new java.util.HashMap());
    }

    public Object parse(Map context) throws TagalogParseException {
        if (this.context != null)
            throw new IllegalStateException("attempt to reuse parser");
        this.context = context;
        doParse();
        return parseResult;
    }

    protected abstract void doParse() throws TagalogParseException;

    protected void startElement(String namespaceUri, String elementName,
                                Attributes attributes)
        throws TagalogParseException
    {
        TagLibrary tagLibrary = configuration.findTagLibrary(namespaceUri);
        Tag tag = tagLibrary.getTag(elementName);
        tag.setContext(context);
        if (currentTag != null)
            tag.setParent(currentTag);
        currentTag = tag;
        tag.begin(elementName, attributes);
    }

    protected void text(char[] characters, int start, int length)
        throws TagalogParseException
    {
        currentTag.text(characters, start, length);
    }

    protected void endElement(String namespaceUri, String elementName)
        throws TagalogParseException
    {
        TagLibrary tagLibrary = configuration.findTagLibrary(namespaceUri);
        Tag tag = currentTag;
        Tag parentTag = tag.getParent();
        Object value = tag.end(elementName);
        tag.setContext(null);
        if (parentTag != null)
            tag.setParent(null);
        currentTag = parentTag;
        tagLibrary.releaseTag(elementName, tag);
        if (parentTag == null)
            parseResult = value;
        else
            parentTag.child(value);
    }
}
