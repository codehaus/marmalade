/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb;

import org.codehaus.marmalade.metamodel.TagInstantiationException;

/**
 * @author jdcasey
 */
public class LateBoundLibraryNotFoundException
    extends TagInstantiationException
{
    private final String taglib;

    public LateBoundLibraryNotFoundException( String taglib )
    {
        super( "late-bound library was not found" );
        this.taglib = taglib;
    }

    public LateBoundLibraryNotFoundException( String taglib, Throwable cause )
    {
        super( "late-bound library was not found", cause );
        this.taglib = taglib;
    }

    public String getLibrary()
    {
        return taglib;
    }
}