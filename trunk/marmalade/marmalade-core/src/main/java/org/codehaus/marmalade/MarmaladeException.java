/* Created on Aug 12, 2004 */
package org.codehaus.marmalade;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author jdcasey
 */
public class MarmaladeException
    extends Exception
{
    /**
     *
     */
    public MarmaladeException()
    {
        //TODO Auto-generated constructor stub
        super();
    }

    /**
     * @param message
     */
    public MarmaladeException( String message )
    {
        //TODO Auto-generated constructor stub
        super( message );
    }

    /**
     * @param cause
     */
    public MarmaladeException( Throwable cause )
    {
        //TODO Auto-generated constructor stub
        super( cause );
    }

    /**
     * @param message
     * @param cause
     */
    public MarmaladeException( String message, Throwable cause )
    {
        //TODO Auto-generated constructor stub
        super( message, cause );
    }

    public String getMessage()
    {
        return super.getMessage();
    }

    public String getLocalizedMessage()
    {
        return super.getLocalizedMessage();
    }

    public void printStackTrace()
    {
        super.printStackTrace();
    }

    public void printStackTrace( PrintStream stream )
    {
        super.printStackTrace( stream );
    }

    public void printStackTrace( PrintWriter writer )
    {
        super.printStackTrace( writer );
    }
}