/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.parsing;

import org.codehaus.marmalade.metamodel.ScriptBuilder;
import org.codehaus.marmalade.reader.ScriptReader;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author jdcasey
 */
public class CachingScriptParser
    extends ScriptParser
{
    private final Map cache = new WeakHashMap();
    
    public CachingScriptParser()
    {
    }
    
    public CachingScriptParser(ScriptReader scriptReader)
    {
        super(scriptReader);
    }

    public void purge( String location )
    {
        cache.remove( location );
    }

    public ScriptBuilder parse( MarmaladeParsingContext context ) throws MarmaladeParsetimeException
    {
        String location = context.getInputLocation();

        ScriptBuilder builder = checkCache( location );

        if ( builder == null )
        {
            builder = super.parse( context );
            cache( location, builder );
        }

        return builder;
    }

    private void cache( String location, ScriptBuilder builder )
    {
        cache.put( location, builder );
    }

    private ScriptBuilder checkCache( String location )
    {
        return (ScriptBuilder) cache.get( location );
    }
}