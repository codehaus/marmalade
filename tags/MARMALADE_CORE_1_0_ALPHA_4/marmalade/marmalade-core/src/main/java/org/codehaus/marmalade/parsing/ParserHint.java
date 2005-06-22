/* Created on Jul 2, 2004 */
package org.codehaus.marmalade.parsing;

/**
 * @author jdcasey
 */
public class ParserHint
{
    private boolean parseChildren = true;

    public ParserHint()
    {
    }

    public boolean parseChildren()
    {
        return parseChildren;
    }

    public ParserHint parseChildren( boolean parseChildren )
    {
        this.parseChildren = parseChildren;

        return this;
    }
}