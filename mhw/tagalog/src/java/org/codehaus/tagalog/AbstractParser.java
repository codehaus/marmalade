/*
 * $Id$
 */

package org.codehaus.tagalog;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.tagalog.pi.PIHandler;

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
     * @param configuration Object holding the parser configuration.
     */
    protected AbstractParser(ParserConfiguration configuration) {
        this.configuration = configuration;
        this.piHandler = configuration.getProcessingInstructionHandler();
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
        TagLibrary tagLibrary;
        Tag tag;

        tagLibrary = configuration.findTagLibrary(namespaceUri);
        if (tagLibrary == null) {
            tagLibrary = NullTagLibrary.INSTANCE;
            noTagLibrary(namespaceUri);
        }
        tag = tagLibrary.getTag(elementName);
        if (tag == null) {
            tag = NullTagLibrary.INSTANCE.getTag(elementName);
            noTag(elementName, namespaceUri);
        }
        tag.setParser(this);
        tag.setContext(context);
        if (currentTag != null)
            tag.setParent(currentTag);
        currentTag = tag;
        try {
            tag.begin(elementName, attributes);
        } catch (TagException e) {
            error(e);
        }
    }

    protected void text(char[] characters, int start, int length)
        throws TagalogParseException
    {
        try {
            currentTag.text(characters, start, length);
        } catch (TagException e) {
            error(e);
        }
    }

    protected void endElement(String namespaceUri, String elementName)
        throws TagalogParseException
    {
        TagLibrary tagLibrary;
        Tag tag;
        Tag parentTag;
        Object value = null;

        tagLibrary = configuration.findTagLibrary(namespaceUri);
        if (tagLibrary == null) {
            tagLibrary = NullTagLibrary.INSTANCE;
            // we've already reported the error when we handled the start tag
        }
        tag = currentTag;
        if (tag instanceof NullTagLibrary.NullTag)
            tagLibrary = NullTagLibrary.INSTANCE;
        parentTag = tag.getParent();
        try {
            value = tag.end(elementName);
        } catch (TagException e) {
            error(e);
        }
        tag.setContext(null);
        if (parentTag != null)
            tag.setParent(null);
        tag.setParser(null);
        currentTag = parentTag;
        tagLibrary.releaseTag(elementName, tag);
        if (parentTag == null) {
            parseResult = value;
        } else {
            try {
                parentTag.child(value);
            } catch (TagException e) {
                error(e);
            }
        }
    }

    //
    // Processing Instructions.
    //

    private PIHandler piHandler;

    public boolean handlingProcessingInstructions() {
        return piHandler != null;
    }

    protected void processingInstruction(String target, String data) {
        piHandler.processingInstruction(target, data, context);
    }

    //
    // Error handling.
    //

    /**
     * Return a <code>Location</code> object representing the current
     * location in the source document.
     *
     * @return The current location.
     */
    public abstract Location getLocation();

    private List parseErrors = new java.util.LinkedList();

    public ParseError[] parseErrors() {
        if (parseErrors.size() == 0)
            return null;
        return (ParseError[]) parseErrors.toArray(ParseError.EMPTY_ARRAY);
    }

    private void error(String message) {
        parseErrors.add(new ParseError(message, getLocation()));
    }

    private void error(TagException e) {
        error(e.getMessage());
    }

    private Set reportedResolutionFailures = new java.util.HashSet();

    private void noTagLibrary(String namespaceUri) {
        namespaceUri = getActualNamespace(namespaceUri);
        if (reportedResolutionFailures.contains(namespaceUri))
            return;
        else
            reportedResolutionFailures.add(namespaceUri);
        error("no tag library for namespace '" + namespaceUri + "'");
    }

    private void noTag(String tag, String namespaceUri) {
        String tagAndNamespace;

        namespaceUri = getActualNamespace(namespaceUri);
        tagAndNamespace = tag + " " + namespaceUri;
        if (reportedResolutionFailures.contains(tagAndNamespace))
            return;
        else
            reportedResolutionFailures.add(tagAndNamespace);
        error("no tag '" + tag + "' in tag library"
              + " for namespace '" + namespaceUri + "'");
    }

    private String getActualNamespace(String namespaceUri) {
        if (namespaceUri == null || namespaceUri.length() == 0)
            return configuration.getDefaultNamespace();
        else
            return namespaceUri;
    }
}
