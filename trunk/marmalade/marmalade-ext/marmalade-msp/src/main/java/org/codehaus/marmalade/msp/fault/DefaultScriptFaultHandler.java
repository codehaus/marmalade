// TODO Attach license header here.
package org.codehaus.marmalade.msp.fault;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jdcasey
 *
 * Created on Nov 26, 2004
 */
public class DefaultScriptFaultHandler
    implements ScriptFaultHandler
{

    public void handleFault( HttpServletRequest request, HttpServletResponse response, Throwable error )
    throws ServletException, IOException
    {
        throw new ServletException("Can't render page: " + error.getLocalizedMessage(), error);
    }

}
