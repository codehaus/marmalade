// TODO Attach license header here.
package org.codehaus.marmalade.msp.ctx;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

/**
 * @author jdcasey
 *
 * Created on Nov 26, 2004
 */
public class DefaultContextProvider
    implements ContextProvider
{

    public MarmaladeExecutionContext buildContext( HttpServletRequest request, HttpServletResponse response )
    {
        Map paramMap = new TreeMap(request.getParameterMap());
        paramMap.put(ContextProvider.REQUEST_CONTEXT_PARAM, request);
        paramMap.put(ContextProvider.RESPONSE_CONTEXT_PARAM, response);
        
        MarmaladeExecutionContext ctx = new DefaultContext();
        ctx.setVariables(paramMap);
        
        return ctx;
    }

    public void exportContext( MarmaladeExecutionContext context, HttpServletRequest request,
        HttpServletResponse response )
    {
        // do nothing for now.
    }

}
