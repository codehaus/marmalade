/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.parsetime;

import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class CachingScriptParser extends ScriptParser
{
    private Map cache = new TreeMap();

    public CachingScriptParser( MarmaladeTaglibResolver marmaladeResolver )
    {
        super( marmaladeResolver );
    }

    public ScriptBuilder parse( File input )
        throws MarmaladeParsetimeException, MarmaladeModelBuilderException
    {
        String location = input.getAbsolutePath();
        
        ScriptBuilder builder = checkCache(location);
        if(builder == null) {
            builder = super.parse( input );
            cache(location, builder);
        }
        
        return builder;
    }

    public ScriptBuilder parse( InputStream input, String location )
        throws MarmaladeParsetimeException, MarmaladeModelBuilderException
    {
        ScriptBuilder builder = checkCache(location);
        if(builder == null) {
            builder = super.parse( input, location );
            cache(location, builder);
        }
        
        return builder;
    }

    public ScriptBuilder parse( Reader input, String location )
        throws MarmaladeParsetimeException, MarmaladeModelBuilderException
    {
        ScriptBuilder builder = checkCache(location);
        if(builder == null) {
            builder = super.parse( input, location );
            cache(location, builder);
        }
        
        return builder;
    }

    public ScriptBuilder parse( URL input )
        throws MarmaladeParsetimeException, MarmaladeModelBuilderException
    {
        String location = input.toExternalForm();
        
        ScriptBuilder builder = checkCache(location);
        if(builder == null) {
            builder = super.parse( input );
            cache(location, builder);
        }
        
        return builder;
    }
    
    private void cache(String location, ScriptBuilder builder) {
        cache.put(location, builder);
    }

    private ScriptBuilder checkCache(String location) {
        return (ScriptBuilder)cache.get(location);
    }

}
