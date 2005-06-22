/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.lb.model.LateBoundTagFactory;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.parsing.ParserHint;

/**
 * @author jdcasey
 */
public interface LateBoundTagLibraryOwner
    extends MarmaladeTag
{
    public abstract void registerTag( String tagName, ParserHint hint, LateBoundTagFactory factory );
}