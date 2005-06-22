/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly.discovery;

import org.codehaus.marmalade.compat.jelly.JellyCompatConstants;
import org.codehaus.marmalade.compat.jelly.metamodel.JellyCompatMarmaladeTaglib;
import org.codehaus.marmalade.discovery.TaglibResolutionStrategy;
import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;
import org.codehaus.marmalade.monitor.log.MarmaladeLog;

/**
 * @author jdcasey
 */
public class JellyCompatTaglibDefinitionStrategy
    implements TaglibResolutionStrategy
{
    private JellyCompatMarmaladeTaglib marmaladeTaglib;
    private MarmaladeLog log;

    public JellyCompatTaglibDefinitionStrategy(  )
    {
        this.marmaladeTaglib = new JellyCompatMarmaladeTaglib(  );
    }

    public MarmaladeTagLibrary resolve( String prefix, String taglib, ClassLoader cloader )
    {
        MarmaladeTagLibrary tlib = null;

        // Greedily snap up anything with a jelly: uri prefix.
        //
        // If you want to use marmalade-native jelly taglibs, use the 
        // new "marmalade:jelly-core" uri format
        //
        if ( JellyCompatConstants.JELLY_TAGLIB_PREFIX.equals( prefix ) )
        {
            tlib = marmaladeTaglib;
        }

        return tlib;
    }
    
    public void setLog( MarmaladeLog log )
    {
        this.log = log;
    }
}
