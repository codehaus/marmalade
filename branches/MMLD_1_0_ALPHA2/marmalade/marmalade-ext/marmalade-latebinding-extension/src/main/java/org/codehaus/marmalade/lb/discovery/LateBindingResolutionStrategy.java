/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb.discovery;

import org.codehaus.marmalade.discovery.TaglibResolutionStrategy;
import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;

/**
 * @author jdcasey
 */
public class LateBindingResolutionStrategy
    implements TaglibResolutionStrategy
{
    private LateBindingTagLibraryResolver resolverTlib = new LateBindingTagLibraryResolver();

    public MarmaladeTagLibrary resolve( String prefix, String taglib )
    {
        if ( resolverTlib.handles( prefix, taglib ) )
        {
            return resolverTlib;
        }
        else
        {
            return null;
        }
    }
}