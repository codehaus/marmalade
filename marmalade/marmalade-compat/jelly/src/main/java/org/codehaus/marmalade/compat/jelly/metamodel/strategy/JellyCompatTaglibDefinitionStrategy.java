/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly.metamodel.strategy;

import org.codehaus.marmalade.compat.jelly.model.JellyCompatMarmaladeTaglib;
import org.codehaus.marmalade.metamodel.strategy.TaglibDefinitionStrategy;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;

/**
 * @author jdcasey
 */
public class JellyCompatTaglibDefinitionStrategy
    implements TaglibDefinitionStrategy
{
    public static final String JELLY_TAGLIB_PREFIX = "jelly";
    private JellyCompatMarmaladeTaglib marmaladeTaglib;

    public JellyCompatTaglibDefinitionStrategy(  )
    {
        this.marmaladeTaglib = new JellyCompatMarmaladeTaglib(  );
    }

    public MarmaladeTagLibrary resolve( String prefix, String taglib )
    {
        MarmaladeTagLibrary tlib = null;

        // Greedily snap up anything with a jelly: uri prefix.
        //
        // If you want to use marmalade-native jelly taglibs, use the 
        // new "marmalade:jelly-core" uri format
        //
        if ( JELLY_TAGLIB_PREFIX.equals( prefix ) )
        {
            tlib = marmaladeTaglib;
        }

        return tlib;
    }
}
