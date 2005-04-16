package org.codehaus.marmalade.compat.ant.discovery;

import org.codehaus.marmalade.compat.ant.metamodel.AntAdapterTagLibrary;
import org.codehaus.marmalade.discovery.AbstractTaglibResolutionStrategy;
import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;

/**
 * @author jdcasey
 */
public class AntBasedDiscoveryStrategy
    extends AbstractTaglibResolutionStrategy
{
    
    private static final String ANT_PREFIX = "ant";

    public MarmaladeTagLibrary resolve( String prefix, String taglib, ClassLoader cloader )
    {
        MarmaladeTagLibrary result = null;
        
        if(ANT_PREFIX.equals(prefix))
        {
            result = new AntAdapterTagLibrary();
        }
        
        return result;
    }

}
