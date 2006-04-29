package org.codehaus.marmalade.metamodel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.codehaus.marmalade.model.AggregationChildTag;
import org.codehaus.marmalade.model.AggregatorMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.parsing.ParserHint;

public class AbstractMultiTagTagLibrary
    extends AbstractMarmaladeTagLibrary
{
    public static final String ORDER_SINGLE_REQUIRED = "";
    public static final String ORDER_SINGLE_OPTIONAL = "?";
    public static final String ORDER_MULTIPLE_REQUIRES_ONE = "+";
    public static final String ORDER_MULTIPLE_OPTIONAL = "*";
    
    private Map taglibPatternTemplates = new HashMap();
    
    private TagPattern currentPattern;
    
    public MarmaladeTag createTag( MarmaladeTagInfo tagInfo )
        throws TagInstantiationException
    {
        String key = TagPattern.getKey( tagInfo.getPrefix(), tagInfo.getTaglib(), tagInfo.getElement() );
        
        MarmaladeTag tag = null;
        
        if ( currentPattern == null )
        {
            TagPattern template = (TagPattern) taglibPatternTemplates.get( key );
            
            if ( template != null )
            {
                tag = super.createTag( tagInfo );
                
                if ( tag instanceof AggregatorMarmaladeTag )
                {
                    currentPattern = new TagPattern( template );
                    currentPattern.setTag( (AggregatorMarmaladeTag) tag );
                }                
            }
        }
        else if ( currentPattern.visit( key ) )
        {
            AggregatorMarmaladeTag parentTag = currentPattern.getTag();
            
            tag = new AggregationChildTag( parentTag );
            
            if ( currentPattern.isFulfilled() )
            {
                currentPattern = null;
            }
        }
        
        return tag;
    }
    
    public TagPattern getTagPattern( String prefix, String taglib, String element )
    {
        String key = TagPattern.getKey( prefix, taglib, element );
        
        TagPattern pattern = (TagPattern) taglibPatternTemplates.get( key );
        if ( pattern == null )
        {
            pattern = new TagPattern( prefix, taglib, element );
            taglibPatternTemplates.put( key, pattern );
        }
        
        return pattern;
    }

    public ParserHint getParserHint( String name )
    {
        return null;
    }

    public static class TagPattern
    {
        
        private final String topPrefix;

        private final String topTaglib;

        private final String topElement;

        private LinkedHashMap visitableKeys = new LinkedHashMap();
        
        private LinkedList visitedKeys = new LinkedList();
        
        private AggregatorMarmaladeTag tag;
        
        TagPattern( String topPrefix, String topTaglib, String topElement )
        {
            this.topPrefix = topPrefix;
            this.topTaglib = topTaglib;
            this.topElement = topElement;
        }
        
        TagPattern( TagPattern original )
        {
            this.topPrefix = original.topPrefix;
            this.topTaglib = original.topTaglib;
            this.topElement = original.topElement;
        }
        
        void setTag( AggregatorMarmaladeTag tag )
        {
            this.tag = tag;
        }
        
        AggregatorMarmaladeTag getTag()
        {
            return tag;
        }
        
        public void registerVisitable( String element, String order )
        {
            String key = getKey( topPrefix, topTaglib, element );
            if ( order == null )
            {
                visitableKeys.put( key, ORDER_SINGLE_REQUIRED );
            }
            else
            {
                visitableKeys.put( key, order );
            }
        }
        
        boolean visit( String key )
        {
            String order = (String) visitableKeys.get( key );
            
            boolean result = false;
            
            if ( ORDER_MULTIPLE_OPTIONAL.equals( order ) || ORDER_MULTIPLE_REQUIRES_ONE.equals( order ) )
            {
                result = true;
            }
            else if ( ORDER_SINGLE_REQUIRED.equals( order ) || ORDER_SINGLE_OPTIONAL.equals( order ) )
            {
                result = !visitedKeys.contains( key );
            }
            
            if ( result )
            {
                visitedKeys.add( key );
            }
            
            return result;
        }
        
        boolean isFulfilled()
        {
            for ( Iterator it = visitableKeys.entrySet().iterator(); it.hasNext(); )
            {
                Map.Entry entry = (Map.Entry) it.next();
                
                String key = (String) entry.getKey();
                
                String order = (String) entry.getValue();
                
                if ( ORDER_MULTIPLE_OPTIONAL.equals( order ) || ORDER_SINGLE_OPTIONAL.equals( order ) )
                {
                    // we don't care...
                    continue;
                }
                else if ( !visitedKeys.contains( key ) )
                {
                    return false;
                }
            }
            
            return true;
        }
        
        String getKey()
        {
            return getKey( topPrefix, topTaglib, topElement );
        }

        public int hashCode()
        {
            return topPrefix.hashCode() + topTaglib.hashCode() + topElement.hashCode();
        }

        public boolean equals( Object other )
        {
            if ( other instanceof TagPattern )
            {
                TagPattern o = (TagPattern) other;

                return topPrefix.equals( o.topPrefix ) && topTaglib.equals( o.topTaglib )
                    && topElement.equals( o.topElement );
            }

            return false;
        }

        static String getKey( String prefix, String taglib, String element )
        {
            return prefix + ":" + taglib + ":" + element;
        }
    }
}
