/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb.metamodel;

import org.codehaus.marmalade.lb.model.LateBoundTagFactory;
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
public class LateBoundTagLibrary
    implements MarmaladeTagLibrary
{
    private Map tagFactories = new TreeMap();

    private Map tagParserHints = new TreeMap();

    public LateBoundTagLibrary()
    {
    }

    public void registerTag( String tag, ParserHint hint, LateBoundTagFactory tagFactory )
    {
        tagFactories.put( tag, tagFactory );
        tagParserHints.put( tag, hint );
    }

    public MarmaladeTag createTag( MarmaladeTagInfo tagInfo ) throws TagInstantiationException
    {
        String tag = tagInfo.getElement();
        LateBoundTagFactory factory = (LateBoundTagFactory) tagFactories.get( tag );

        return factory.newTag();
    }

    public ParserHint getParserHint( String name )
    {
        return (ParserHint) tagParserHints.get( name );
    }
}