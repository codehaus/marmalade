// TODO Attach license header here.
package org.codehaus.marmalade.msp.support;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.marmalade.msp.MSPHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author jdcasey Created on Nov 26, 2004
 */
public class MSPServlet
    extends HttpServlet
{

    private ServletConfig servletConfig;

    public void init( ServletConfig servletConfig ) throws ServletException
    {
        this.servletConfig = servletConfig;
    }

    protected void service( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
        IOException
    {
        System.out.println("Serving Marmalade Page.");
        
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext( servletConfig
            .getServletContext() );
        
        MSPHandler mspHandler = (MSPHandler) ctx.getBean( MSPHandler.BEAN_ID );
        
        mspHandler.execute( request, response );
    }

}