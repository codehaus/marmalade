/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb;

import org.codehaus.marmalade.metamodel.TagInstantiationException;

/**
 * @author jdcasey
 */
public class LateBoundTagNotFoundException
    extends TagInstantiationException
{
    private final String taglib;

    private final String tag;

    public LateBoundTagNotFoundException( String taglib, String tag )
    {
        super( "late-bound tag was not found" );
        this.taglib = taglib;
        this.tag = tag;
    }

    public LateBoundTagNotFoundException( String taglib, String tag, Throwable cause )
    {
        super( "late-bound tag was not found", cause );
        this.taglib = taglib;
        this.tag = tag;
    }

    public String getLibrary()
    {
        return taglib;
    }

    public String getTag()
    {
        return tag;
    }
}