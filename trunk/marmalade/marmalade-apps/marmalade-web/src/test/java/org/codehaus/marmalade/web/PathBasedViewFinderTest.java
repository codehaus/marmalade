/* Created on Aug 25, 2004 */
package org.codehaus.marmalade.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.marmalade.parsing.MarmaladeParsingContext;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class PathBasedViewFinderTest
    extends MockObjectTestCase
{

    private File tempDir;
    private static final String TEST_SCRIPT_NAME = "test.mmld";
    private File scriptFile;
    
    protected void setUp() throws IOException {
        tempDir = new File("PathBasedViewFinderTest-dir");
        tempDir.mkdirs();
        
        scriptFile = new File(tempDir, TEST_SCRIPT_NAME);
        FileWriter writer = new FileWriter(scriptFile);
        writer.write("<c:set var=\"test\" value=\"value\" xmlns:c=\"marmalade:core\"/>");
        writer.flush();
        writer.close();
        
        scriptFile.deleteOnExit();
        tempDir.deleteOnExit();
    }

    public void testShouldConstructWithViewDirectory() {
        PathBasedViewFinder finder = new PathBasedViewFinder("/path/to/views");
    }
    
    public void testShouldReturnSourceWithRequestURIAppendedToViewDir() throws IOException {
        PathBasedViewFinder finder = new PathBasedViewFinder(tempDir.getCanonicalPath());
        finder.setPathSearch("\\.jsp");
        finder.setPathReplace(".mmld");
        
        Mock reqMock = mock(HttpServletRequest.class);
        reqMock.expects(once()).method("getRequestURI").withNoArguments().will(returnValue("test.jsp"));
        HttpServletRequest req = (HttpServletRequest)reqMock.proxy();
        
        ViewSource source = finder.find(req);
        
        MarmaladeParsingContext ctx = source.openContext();
        
        System.out.println("input location is: " + ctx.getInputLocation());
        assertEquals(scriptFile.getCanonicalPath(), ctx.getInputLocation());
    }
    
}
