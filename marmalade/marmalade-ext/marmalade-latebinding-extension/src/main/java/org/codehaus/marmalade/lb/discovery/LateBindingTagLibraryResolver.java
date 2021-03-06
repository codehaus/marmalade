/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb.discovery;

import org.codehaus.marmalade.lb.LateBoundLibraryNotFoundException;
import org.codehaus.marmalade.lb.LateBoundTagNotFoundException;
import org.codehaus.marmalade.lb.taglib.LateBindingDefinitionTagLibrary;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;
import org.codehaus.marmalade.metamodel.TagInstantiationException;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.parsing.ParserHint;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class LateBindingTagLibraryResolver
    implements MarmaladeTagLibrary
{
    private Map taglibsByPrefixAndUri = new TreeMap();

    public LateBindingTagLibraryResolver()
    {
        taglibsByPrefixAndUri.put( LateBindingDefinitionTagLibrary.LB_DEFINITION_TAGLIB_URI,
            new LateBindingDefinitionTagLibrary( this ) );
    }

    public boolean handles( String prefix, String taglib )
    {
        return taglibsByPrefixAndUri.containsKey( prefix + ":" + taglib );
    }

    public void registerTagLibrary( String prefix, String taglib, MarmaladeTagLibrary lib )
    {
        String key = prefix + ":" + taglib;
        
        taglibsByPrefixAndUri.put( prefix + ":" + taglib, lib );
    }

    public MarmaladeTag createTag( MarmaladeTagInfo tagInfo ) throws TagInstantiationException
    {
        String key = tagInfo.getScheme() + ":" + tagInfo.getTaglib();
        
        MarmaladeTagLibrary tlib = (MarmaladeTagLibrary) taglibsByPrefixAndUri.get( key );

        if ( tlib == null )
        {
            throw new LateBoundLibraryNotFoundException( tagInfo );
        }

        MarmaladeTag result = null;

        if ( tlib != null )
        {
            result = tlib.createTag( tagInfo );
        }

        if ( result == null )
        {
            throw new LateBoundTagNotFoundException( tagInfo );
        }

        return result;
    }

    public ParserHint getParserHint( String name )
    {
        return new ParserHint().parseChildren( true );
    }
}