// TODO Attach license header here.
package org.codehaus.marmalade.msp.cache;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author jdcasey
 *
 * Created on Nov 26, 2004
 */
public class NoExpireCacheManagerTest
    extends MockObjectTestCase
{
    
    public void testShouldStoreAndRetrieveMarmaladeScriptForRequestURI()
    {
        Mock requestMock = mock(HttpServletRequest.class);
        requestMock.expects(once()).method("getRequestURI").withNoArguments().will(returnValue("/test/path"));
        
        HttpServletRequest request = (HttpServletRequest)requestMock.proxy();
        
        Mock rootTagMock = mock(MarmaladeTag.class);
        MarmaladeTag rootTag = (MarmaladeTag)rootTagMock.proxy();
        
        MarmaladeScript script = new MarmaladeScript("/test/path", rootTag);
        
        NoExpireCacheManager mgr = new NoExpireCacheManager();
        mgr.cache(request, script);
        
        Mock requestMock2 = mock(HttpServletRequest.class);
        requestMock2.expects(once()).method("getRequestURI").withNoArguments().will(returnValue("/test/path"));
        
        HttpServletRequest request2 = (HttpServletRequest)requestMock2.proxy();
        
        MarmaladeScript script2 = mgr.lookup(request2);
        
        assertEquals(script, script2);
    }
    
}
