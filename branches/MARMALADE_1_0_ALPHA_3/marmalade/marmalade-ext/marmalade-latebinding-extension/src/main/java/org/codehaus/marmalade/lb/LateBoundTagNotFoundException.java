/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.TagInstantiationException;

/**
 * @author jdcasey
 */
public class LateBoundTagNotFoundException
    extends TagInstantiationException
{
    public LateBoundTagNotFoundException( MarmaladeTagInfo tagInfo)
    {
        super( tagInfo, "late-bound tag was not found" );
    }

    public LateBoundTagNotFoundException( MarmaladeTagInfo tagInfo, Throwable cause )
    {
        super( tagInfo, "late-bound tag was not found", cause );
    }

}