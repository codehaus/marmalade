/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.parsetime;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class CachingScriptParser extends ScriptParser
{
    private Map cache = new TreeMap();

    public CachingScriptParser(  )
    {
    }

    public ScriptBuilder parse( MarmaladeParsingContext context )
        throws MarmaladeParsetimeException, MarmaladeModelBuilderException
    {
        String location = context.getInputLocation();
        
        ScriptBuilder builder = checkCache(location);
        if(builder == null) {
            builder = super.parse( context );
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
