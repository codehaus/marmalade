// TODO Attach license header here.
package org.codehaus.marmalade.msp.cache;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.marmalade.model.MarmaladeScript;

/**
 * @author jdcasey
 *
 * Created on Nov 26, 2004
 */
public class NoExpireCacheManager
    implements CacheManager
{
    
    private Map cache = new TreeMap();

    public void cache( HttpServletRequest request, MarmaladeScript script )
    {
        this.cache.put(request.getRequestURI(), script);
    }

    public MarmaladeScript lookup( HttpServletRequest request )
    {
        return (MarmaladeScript) cache.get(request.getRequestURI());
    }

}
