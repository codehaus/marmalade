// TODO Attach license header here.
package org.codehaus.marmalade.msp.finder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.marmalade.msp.InvalidHandlerStateException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author jdcasey
 *
 * Created on Nov 26, 2004
 */
public class FileBasedScriptFinderTest
    extends MockObjectTestCase
{
    
    public void testShouldFailWithStateExceptionWhenBasePathNotSet() throws IOException
    {
        Mock requestMock = mock(HttpServletRequest.class);
        HttpServletRequest request = (HttpServletRequest)requestMock.proxy();
        
        FileBasedScriptFinder finder = new FileBasedScriptFinder();
        
        try
        {
            finder.getScript(request);
            fail("Should fail because basePath properties is not set");
        }
        catch(InvalidHandlerStateException e)
        {
            //should catch this exception.
        }
    }
    
    public void testShouldReturnReaderToScriptFileBasedOnRequestURI() throws IOException
    {
        String path = "/testScript.mmld";
        
        String script = "<?xml version=\"1.0\"?><c:out xmlns:c=\"marmalade:core\" value=\"Test\"/>";
        
        File tmpDir = new File("testTemp/");
        if(!tmpDir.exists())
        {
            tmpDir.mkdirs();
        }
        
        PrintWriter scriptWriter = new PrintWriter(new FileWriter(new File(tmpDir, path)));
        scriptWriter.print(script);
        scriptWriter.flush();
        scriptWriter.close();
        
        try
        {
            Mock requestMock = mock( HttpServletRequest.class );
            requestMock.expects( once() ).method( "getRequestURI" ).withNoArguments().will( returnValue( path ) );

            HttpServletRequest request = (HttpServletRequest) requestMock.proxy();

            FileBasedScriptFinder finder = new FileBasedScriptFinder();
            finder.setBasePath("testTemp");

            Reader reader = finder.getScript( request );
            
            StringBuffer buffer = new StringBuffer();
            
            char[] temp = new char[60];
            int read = -1;
            
            while((read = reader.read(temp)) > -1)
            {
                buffer.append(temp, 0, read);
            }
            
            assertEquals(script, buffer.toString());
        }
        finally
        {
            new File(tmpDir, path).delete();
        }
    }
    
    public void testShouldReturnReaderToScriptFileFromInterpretedPath() throws IOException
    {
        String path = "/path/testScript.mmld";
        String interpreted = "/testScript.mmld";
        
        String script = "<?xml version=\"1.0\"?><c:out xmlns:c=\"marmalade:core\" value=\"Test\"/>";
        
        PrintWriter scriptWriter = new PrintWriter(new FileWriter(new File("testTemp" + interpreted)));
        scriptWriter.print(script);
        scriptWriter.flush();
        scriptWriter.close();
        
        try
        {
            Mock requestMock = mock( HttpServletRequest.class );
            requestMock.expects( once() ).method( "getRequestURI" ).withNoArguments().will( returnValue( path ) );

            HttpServletRequest request = (HttpServletRequest) requestMock.proxy();
            
            Mock interpreterMock = mock(PathInterpreter.class);
            interpreterMock.expects(once()).method("interpret").with(eq(path)).will(returnValue(interpreted));
            
            PathInterpreter interpeter = (PathInterpreter)interpreterMock.proxy();

            FileBasedScriptFinder finder = new FileBasedScriptFinder();
            finder.setBasePath("testTemp");
            finder.setPathInterpreter(interpeter);

            Reader reader = finder.getScript( request );
            
            StringBuffer buffer = new StringBuffer();
            
            char[] temp = new char[60];
            int read = -1;
            
            while((read = reader.read(temp)) > -1)
            {
                buffer.append(temp, 0, read);
            }
            
            assertEquals(script, buffer.toString());
        }
        finally
        {
            new File(path).delete();
        }
    }
    
//    public void testShouldFailNotImplementedYet()
//    {
//        fail("This class needs to be tested.");
//    }

}
