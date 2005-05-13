/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.TagInstantiationException;

/**
 * @author jdcasey
 */
public class LateBoundLibraryNotFoundException
    extends TagInstantiationException
{
    public LateBoundLibraryNotFoundException( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo, "late-bound library was not found" );
    }

    public LateBoundLibraryNotFoundException( MarmaladeTagInfo tagInfo, Throwable cause )
    {
        super( tagInfo, "late-bound library was not found", cause );
    }

}