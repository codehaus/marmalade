// TODO Attach license header here.
package org.codehaus.marmalade.msp.fault;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author jdcasey Created on Nov 26, 2004
 */
public class DefaultScriptFaultHandlerTest
    extends MockObjectTestCase
{

    public void testShouldThrowServletExceptionOnError() throws IOException
    {
        Mock requestMock = mock(HttpServletRequest.class);
        HttpServletRequest request = (HttpServletRequest)requestMock.proxy();
        
        Mock responseMock = mock(HttpServletResponse.class);
        HttpServletResponse response = (HttpServletResponse)responseMock.proxy();
        
        String message = "This is a test message";
        IllegalArgumentException error = new IllegalArgumentException(message);
        
        DefaultScriptFaultHandler handler = new DefaultScriptFaultHandler();
        
        try
        {
            handler.handleFault(request, response, error);
            fail("Should throw a ServletException.");
        }
        catch(ServletException e)
        {
            //should catch this exception.
            assertTrue(e.getLocalizedMessage().indexOf(message) > -1);
        }
    }
    
}