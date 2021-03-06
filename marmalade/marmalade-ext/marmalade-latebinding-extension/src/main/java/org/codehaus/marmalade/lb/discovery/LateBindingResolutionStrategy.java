/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb.discovery;

import org.codehaus.marmalade.discovery.TaglibResolutionStrategy;
import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;
import org.codehaus.marmalade.monitor.log.MarmaladeLog;

/**
 * @author jdcasey
 */
public class LateBindingResolutionStrategy
    implements TaglibResolutionStrategy
{
    private LateBindingTagLibraryResolver resolverTlib = new LateBindingTagLibraryResolver();
    private MarmaladeLog log;

    public MarmaladeTagLibrary resolve( String prefix, String taglib, ClassLoader classloader )
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

    public void setLog( MarmaladeLog log )
    {
        this.log = log;
    }
}