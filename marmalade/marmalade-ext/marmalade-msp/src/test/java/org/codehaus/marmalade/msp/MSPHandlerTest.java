// TODO Attach license header here.
package org.codehaus.marmalade.msp;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import org.codehaus.marmalade.msp.ctx.ContextProvider;
import org.codehaus.marmalade.msp.fault.ScriptFaultHandler;
import org.codehaus.marmalade.msp.finder.ScriptFinder;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author jdcasey
 *
 * Created on Nov 26, 2004
 */
public class MSPHandlerTest
    extends MockObjectTestCase
{
    
    public void testShouldThrowThreeExceptionsWhenNoStateConfigured() throws ServletException, IOException
    {
//        Mock requestMock = mock(HttpServletRequest.class);
//        HttpServletRequest request = (HttpServletRequest)requestMock.proxy();
//        
//        Mock responseMock = mock(HttpServletResponse.class);
//        HttpServletResponse response = (HttpServletResponse)responseMock.proxy();
        
        MSPHandler handler = new MSPHandler();
        try
        {
            handler.execute( null, null );
            fail("Should throw three state violation exceptions.");
        }
        catch ( InvalidHandlerStateException e )
        {
            assertEquals(3, e.getStateViolations().size());
        }
        
    }
    
    public void testShouldThrowExceptionWhenScriptFinderNotConfigured() throws ServletException, IOException
    {
        MSPHandler handler = new MSPHandler();
        
//        Mock sfMock = mock(ScriptFinder.class);
//        ScriptFinder finder = (ScriptFinder)sfMock.proxy();
//        
//        handler.setScriptFinder(finder);
        
        Mock cpMock = mock(ContextProvider.class);
        ContextProvider ctxProvider = (ContextProvider)cpMock.proxy();
        
        handler.setContextProvider(ctxProvider);
        
        Mock sfhMock = mock(ScriptFaultHandler.class);
        ScriptFaultHandler faultHandler = (ScriptFaultHandler)sfhMock.proxy();
        
        handler.setScriptFaultHandler(faultHandler);
        
        try
        {
            handler.execute( null, null );
            fail("Should throw state violation exception for missing script finder.");
        }
        catch ( InvalidHandlerStateException e )
        {
            assertEquals(1, e.getStateViolations().size());
        }
        
    }
    
    public void testShouldThrowExceptionWhenContextProviderNotConfigured() throws ServletException, IOException
    {
        MSPHandler handler = new MSPHandler();
        
        Mock sfMock = mock(ScriptFinder.class);
        ScriptFinder finder = (ScriptFinder)sfMock.proxy();
        
        handler.setScriptFinder(finder);
        
//        Mock cpMock = mock(ContextProvider.class);
//        ContextProvider ctxProvider = (ContextProvider)cpMock.proxy();
//        
//        handler.setContextProvider(ctxProvider);
        
        Mock sfhMock = mock(ScriptFaultHandler.class);
        ScriptFaultHandler faultHandler = (ScriptFaultHandler)sfhMock.proxy();
        
        handler.setScriptFaultHandler(faultHandler);
        
        try
        {
            handler.execute( null, null );
            fail("Should throw state violation exception for missing context provider.");
        }
        catch ( InvalidHandlerStateException e )
        {
            assertEquals(1, e.getStateViolations().size());
        }
        
    }
    
    public void testShouldThrowExceptionWhenFaultHandlerNotConfigured() throws ServletException, IOException
    {
        MSPHandler handler = new MSPHandler();
        
        Mock sfMock = mock(ScriptFinder.class);
        ScriptFinder finder = (ScriptFinder)sfMock.proxy();
        
        handler.setScriptFinder(finder);
        
        Mock cpMock = mock(ContextProvider.class);
        ContextProvider ctxProvider = (ContextProvider)cpMock.proxy();
        
        handler.setContextProvider(ctxProvider);
        
//        Mock sfhMock = mock(ScriptFaultHandler.class);
//        ScriptFaultHandler faultHandler = (ScriptFaultHandler)sfhMock.proxy();
//        
//        handler.setScriptFaultHandler(faultHandler);
        
        try
        {
            handler.execute( null, null );
            fail("Should throw state violation exception for missing fault handler.");
        }
        catch ( InvalidHandlerStateException e )
        {
            assertEquals(1, e.getStateViolations().size());
        }
        
    }
    
    public void testShouldExecuteScriptWithoutCacheManager() throws ServletException, IOException
    {
        MSPHandler handler = new MSPHandler();
        
        Mock sfMock = mock(ScriptFinder.class);
        
        Mock requestMock = mock(HttpServletRequest.class);
        requestMock.expects(once()).method("getRequestURI").withNoArguments().will(returnValue("/test/path"));
        
        HttpServletRequest request = (HttpServletRequest)requestMock.proxy();
        
        String script = "<?xml version=\"1.0\"?><c:out xmlns:c=\"marmalade:core\" value=\"test ${key}\"/>";
        
        sfMock.expects(once()).method("getScript").with(eq(request)).will(returnValue(new StringReader(script)));
        
        ScriptFinder finder = (ScriptFinder)sfMock.proxy();
        
        handler.setScriptFinder(finder);
        
        Mock cpMock = mock(ContextProvider.class);
        
        Map context = new TreeMap();
        context.put("key", "value");
        
        context.put(ContextProvider.REQUEST_CONTEXT_PARAM, request);
        
        StringWriter sWriter = new StringWriter();
        PrintWriter out = new PrintWriter(sWriter);
        
        Mock responseMock = mock(HttpServletResponse.class);
        responseMock.expects(once()).method("getWriter").withNoArguments().will(returnValue(out));
        
        HttpServletResponse response = (HttpServletResponse)responseMock.proxy();
        
        context.put(ContextProvider.RESPONSE_CONTEXT_PARAM, response);
        
        MarmaladeExecutionContext ctx = new DefaultContext();
        ctx.setVariables(context);
        
        cpMock.expects(once()).method("buildContext").with(eq(request), eq(response)).will(returnValue(ctx));
        cpMock.expects(once()).method("exportContext").with(eq(ctx), eq(request), eq(response)).isVoid();
        
        ContextProvider ctxProvider = (ContextProvider)cpMock.proxy();
        
        handler.setContextProvider(ctxProvider);
        
        Mock sfhMock = mock(ScriptFaultHandler.class);
        ScriptFaultHandler faultHandler = (ScriptFaultHandler)sfhMock.proxy();
        
        handler.setScriptFaultHandler(faultHandler);
        
        handler.execute( request, response );
        
        String result = sWriter.toString();
        assertEquals("test value", result);
        
    }
    
    public static final class TestJspWriter extends JspWriter
    {
        
        private PrintWriter printWriter;

        TestJspWriter(PrintWriter printWriter)
        {
            super(1024, true);
            
            this.printWriter = printWriter;
        }

        public void newLine() throws IOException
        {
            printWriter.print('\n');
        }

        public void print( boolean bool ) throws IOException
        {
            printWriter.print(bool);
        }

        public void print( char ch ) throws IOException
        {
            printWriter.print(ch);
        }

        public void print( int igr ) throws IOException
        {
            printWriter.print(igr);
        }

        public void print( long lng ) throws IOException
        {
            printWriter.print(lng);
        }

        public void print( float flt ) throws IOException
        {
            printWriter.print(flt);
        }

        public void print( double dbl ) throws IOException
        {
            printWriter.print(dbl);
        }

        public void print( char[] ch ) throws IOException
        {
            printWriter.print(ch);
        }

        public void print( String str ) throws IOException
        {
            printWriter.print(str);
        }

        public void print( Object obj ) throws IOException
        {
            printWriter.print(obj);
        }

        public void println() throws IOException
        {
            printWriter.print('\n');
        }

        public void println( boolean bool ) throws IOException
        {
            print(bool);
            println();
        }

        public void println( char ch ) throws IOException
        {
            print(ch);
            println();
        }

        public void println( int igr ) throws IOException
        {
            print(igr);
            println();
        }

        public void println( long lng ) throws IOException
        {
            print(lng);
            println();
        }

        public void println( float flt ) throws IOException
        {
            print(flt);
            println();
        }

        public void println( double dbl ) throws IOException
        {
            print(dbl);
            println();
        }

        public void println( char[] ch ) throws IOException
        {
            print(ch);
            println();
        }

        public void println( String str ) throws IOException
        {
            print(str);
            println();
        }

        public void println( Object obj ) throws IOException
        {
            print(obj);
            println();
        }

        public void clear() throws IOException
        {
            throw new IOException("Not allowed in a test.");
        }

        public void clearBuffer() throws IOException
        {
            throw new IOException("Not allowed in a test.");
        }

        public void flush() throws IOException
        {
        }

        public void close() throws IOException
        {
        }

        public int getRemaining()
        {
            return 0;
        }

        public void write( char[] cbuf, int off, int len ) throws IOException
        {
            printWriter.write(cbuf, off, len);
        }
    }
    
}
