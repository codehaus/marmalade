/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.model.beans;

/**
 * @author jdcasey
 */
public class TestException
    extends Exception
{

    public TestException()
    {
    }

    public TestException( String message )
    {
        super( message );
    }

    public TestException( Throwable cause )
    {
        super( cause );
    }

    public TestException( String message, Throwable cause )
    {
        super( message, cause );
    }

}
