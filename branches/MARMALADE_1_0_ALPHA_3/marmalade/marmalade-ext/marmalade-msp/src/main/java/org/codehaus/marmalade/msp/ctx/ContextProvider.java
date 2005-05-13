// TODO Attach license header here.
package org.codehaus.marmalade.msp.ctx;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

/**
 * @author jdcasey
 *
 * Created on Nov 26, 2004
 */
public interface ContextProvider
{
    
    public static final String REQUEST_CONTEXT_PARAM = "request";
    public static final String RESPONSE_CONTEXT_PARAM = "response";
    
    MarmaladeExecutionContext buildContext(HttpServletRequest request, HttpServletResponse response);
    
    void exportContext(MarmaladeExecutionContext context, HttpServletRequest request, HttpServletResponse response);

}
