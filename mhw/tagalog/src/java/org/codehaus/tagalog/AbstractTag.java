/*
 * $Id$
 */

package org.codehaus.tagalog;

import java.util.Map;

/**
 * AbstractTag
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public abstract class AbstractTag implements Tag {
    private Map context;

    private Tag parent;

    public void setContext(Map context) {
        this.context = context;
    }

    protected Map getContext() {
        return context;
    }

    public void setParent(Tag parent) {
        this.parent = parent;
    }

    public Tag getParent() {
        return parent;
    }

    public void begin(String elementName, Attributes attribute)
        throws TagalogParseException
    {
    }

    public void text(char[] characters, int start, int length)
        throws TagalogParseException
    {
    }

    public void child(Object child) throws TagalogParseException {
    }

    public Object end(String elementName) throws TagalogParseException {
        return null;
    }

    public boolean recycle() {
        return true;
    }

    //
    // Convenience methods to make tag implementation easier.
    //

    protected String requireAttribute(String attributeName,
                                      Attributes attributes)
        throws TagalogParseException
    {
        return TagUtils.requireAttribute(attributeName, attributes);
    }

    protected Tag findAncestorWithClass(Class tagClass) {
        return TagUtils.findAncestorWithClass(getParent(), tagClass);
    }

    protected Tag requireAncestor(String tagName, Class tagClass)
        throws TagalogParseException
    {
        return TagUtils.requireAncestor(getParent(), tagName, tagClass);
    }
}
