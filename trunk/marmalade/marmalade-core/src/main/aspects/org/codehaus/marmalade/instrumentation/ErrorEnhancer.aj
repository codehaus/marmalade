/* Created on Aug 10, 2004 */
package org.codehaus.marmalade.instrumentation;

import org.codehaus.marmalade.MarmaladeException;
import java.io.PrintWriter;
import java.io.PrintStream;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

/**
 * @author jdcasey
 */
public abstract aspect ErrorEnhancer {
    
    private static final String ORIGINAL_MESSAGE_PREFIX = "Original Message: ";
    
    private String MarmaladeException._originalMessage;
    
    protected String getOriginalMessage(MarmaladeException ex) {
        return ex._originalMessage;
    }
    

    
    protected pointcut firstTimeExceptionMethodCalls(MarmaladeException ex):
        within(MarmaladeException) &&
        !cflowbelow(execution(* MarmaladeException+.*(..))) &&
        !cflowbelow(execution(* ErrorEnhancer+.*(..))) &&
        this(ex);
    
    protected pointcut stacktraceWithPrintWriter(MarmaladeException ex, PrintWriter writer):
        execution(public void *.printStackTrace(PrintWriter)) && args(writer) &&
        firstTimeExceptionMethodCalls(ex);
        
    protected pointcut stacktraceWithPrintStream(MarmaladeException ex, PrintStream stream):
        execution(public void *.printStackTrace(PrintStream)) && args(stream) &&
        firstTimeExceptionMethodCalls(ex);
        
    protected pointcut stacktraceWithNothing(MarmaladeException ex):
        execution(public void *.printStackTrace()) &&
        firstTimeExceptionMethodCalls(ex);
        
    protected pointcut getAnyMessage(MarmaladeException ex):
        execution(public String MarmaladeException+.get*Message()) &&
        firstTimeExceptionMethodCalls(ex);
    
    void around(MarmaladeException ex): stacktraceWithNothing(ex)
    {
        ErrorUtil.printStackTraceWithAlternateMessage(ex, ex.getLocalizedMessage(), System.err);
    }
    
    void around(MarmaladeException ex, PrintStream stream): stacktraceWithPrintStream(ex, stream)
    {
        ErrorUtil.printStackTraceWithAlternateMessage(ex, ex.getLocalizedMessage(), stream);
    }
    
    void around(MarmaladeException ex, PrintWriter writer): stacktraceWithPrintWriter(ex, writer)
    {
        ErrorUtil.printStackTraceWithAlternateMessage(ex, ex.getLocalizedMessage(), writer);
    }
    
    after(MarmaladeException ex, String message): 
        execution(MarmaladeException.new(String,..)) &&
        args(message,..) && this(ex)
    {
        // not sure what happens when multiple concrete impls of 
        // this aspect advise at once...so we check.
        if(ex._originalMessage == null) {
            ex._originalMessage = message;
        }
    }
    
    /*
    Object around(MarmaladeException ex): getAnyMessage(ex)
    {
        String result = (String)proceed(ex);
        
        if(result.indexOf(ORIGINAL_MESSAGE_PREFIX) < 0) {
            result = ORIGINAL_MESSAGE_PREFIX + result;
        }
        
        return result;
    }
    */
}
