/* Created on Aug 10, 2004 */
package org.codehaus.marmalade.instrumentation.testExceptionPkg;

import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class TestRBException
    extends MarmaladeExecutionException
{
    /**
     *
     */
    public TestRBException()
    {
        //TODO Auto-generated constructor stub
        super();
    }

    /**
     * @param cause
     */
    public TestRBException( Throwable cause )
    {
        //TODO Auto-generated constructor stub
        super( cause );
    }

    /**
     * @param message
     */
    public TestRBException( String message )
    {
        //TODO Auto-generated constructor stub
        super( message );
    }

    /**
     * @param message
     * @param cause
     */
    public TestRBException( String message, Throwable cause )
    {
        //TODO Auto-generated constructor stub
        super( message, cause );
    }

    public static void main( String[] args )
    {
    }
}