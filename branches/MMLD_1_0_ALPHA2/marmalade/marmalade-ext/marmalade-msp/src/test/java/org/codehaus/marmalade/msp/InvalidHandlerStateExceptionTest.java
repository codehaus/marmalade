// TODO Attach license header here.
package org.codehaus.marmalade.msp;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author jdcasey
 *
 * Created on Nov 26, 2004
 */
public class InvalidHandlerStateExceptionTest
    extends TestCase
{
    
    public void testShouldConstructWithErrorListAndEnumerateThemInStackTrace() {
        String firstMessage = "This is the first test.";
        String secondMessage = "This is the second test.";
        
        List errors = new ArrayList();
        errors.add(new IllegalArgumentException(firstMessage));
        errors.add(new IllegalArgumentException(secondMessage));
        
        InvalidHandlerStateException toTest = new InvalidHandlerStateException(errors);
        
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);
        
        toTest.printStackTrace(pWriter);
        
        String result = sWriter.toString();
        
        int firstIdx = result.indexOf(firstMessage);
        int secondIdx = result.indexOf(secondMessage);
        
        assertTrue(firstIdx > -1);
        assertTrue(secondIdx > -1);
        assertTrue(firstIdx < secondIdx);
    }

}
