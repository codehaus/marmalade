// TODO Attach license header here.
package org.codehaus.marmalade.msp.cache;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.marmalade.model.MarmaladeScript;

/**
 * @author jdcasey
 *
 * Created on Nov 26, 2004
 */
public interface CacheManager
{
    
    void cache(HttpServletRequest request, MarmaladeScript script);

    MarmaladeScript lookup(HttpServletRequest request);
    
}
