/* Created on Aug 10, 2004 */
package org.codehaus.marmalade.instrumentation;

import org.codehaus.marmalade.MarmaladeException;

import java.io.PrintStream;
import java.io.PrintWriter;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author jdcasey
 */
public class ErrorUtil
{
    public static final String ERROR_MESSAGES_RB = "org.codehaus.marmalade.errors";

    private ErrorUtil()
    {
    }

    public static void printStackTraceWithAlternateMessage( MarmaladeException ex, String altMessage, PrintStream output )
    {
        output.println( altMessage );

        StackTraceElement[] traceElements = ex.getStackTrace();

        for ( int i = 0; i < traceElements.length; i++ )
        {
            StackTraceElement traceElement = traceElements[i];

            output.println( "\tat " + traceElement );
        }

        Throwable cause = ex.getCause();

        if ( cause != null )
        {
            output.print( "Caused by: " );
            cause.printStackTrace( output );
        }
    }

    public static void printStackTraceWithAlternateMessage( MarmaladeException ex, String altMessage, PrintWriter output )
    {
        output.println( altMessage );

        StackTraceElement[] traceElements = ex.getStackTrace();

        for ( int i = 0; i < traceElements.length; i++ )
        {
            StackTraceElement traceElement = traceElements[i];

            output.println( "\tat " + traceElement );
        }

        Throwable cause = ex.getCause();

        if ( cause != null )
        {
            output.print( "Caused by: " );
            cause.printStackTrace( output );
        }
    }

    public static String getMessage( String errorMessage )
    {
        String rbMessage = null;

        try
        {
            ResourceBundle rb = ResourceBundle.getBundle( ERROR_MESSAGES_RB );

            rbMessage = rb.getString( String.valueOf( errorMessage ) );
        }
        catch ( MissingResourceException e )
        {
            // skip it.
        }

        String result = errorMessage;

        if ( rbMessage != null )
        {
            result = rbMessage;
        }

        return result;
    }
}