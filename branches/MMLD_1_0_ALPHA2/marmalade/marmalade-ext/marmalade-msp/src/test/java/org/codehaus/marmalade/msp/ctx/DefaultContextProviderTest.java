// TODO Attach license header here.
package org.codehaus.marmalade.msp.ctx;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author jdcasey
 *
 * Created on Nov 26, 2004
 */
public class DefaultContextProviderTest
    extends MockObjectTestCase
{
    
    public void testShouldBuildContextFromParameterMapWithRequestAndResponse() 
    throws ExpressionEvaluationException
    {
        Map params = new TreeMap();
        
        String key = "key";
        String value = "value";
        
        params.put(key, value);
        
        Mock requestMock = mock(HttpServletRequest.class);
        requestMock.expects(once()).method("getParameterMap").withNoArguments().will(returnValue(params));
        
        HttpServletRequest request = (HttpServletRequest)requestMock.proxy();
        
        Mock responseMock = mock(HttpServletResponse.class);
        HttpServletResponse response = (HttpServletResponse)responseMock.proxy();
        
        DefaultContextProvider provider = new DefaultContextProvider();
        MarmaladeExecutionContext ctx = provider.buildContext(request, response);
        
        assertEquals(value, ctx.getVariable(key, null));
        assertEquals(request, ctx.getVariable(ContextProvider.REQUEST_CONTEXT_PARAM, null));
        assertEquals(response, ctx.getVariable(ContextProvider.RESPONSE_CONTEXT_PARAM, null));
    }
    
    public void testShouldDoNothingWhenExportContextIsCalled()
    {
        Mock requestMock = mock(HttpServletRequest.class);
        HttpServletRequest request = (HttpServletRequest)requestMock.proxy();
        
        Mock responseMock = mock(HttpServletResponse.class);
        HttpServletResponse response = (HttpServletResponse)responseMock.proxy();
        
        Mock contextMock = mock(MarmaladeExecutionContext.class);
        MarmaladeExecutionContext context = (MarmaladeExecutionContext)contextMock.proxy();
        
        DefaultContextProvider provider = new DefaultContextProvider();
        provider.exportContext(context, request, response);
    }
    
}
