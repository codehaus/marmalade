/* Created on Aug 12, 2004 */
package org.codehaus.marmalade;


/**
 * @author jdcasey
 */
public class MarmaladeException
    extends Exception
{
    public MarmaladeException()
    {
    }

    public MarmaladeException( String message )
    {
        super( message );
    }

    public MarmaladeException( Throwable cause )
    {
        super( cause );
    }

    public MarmaladeException( String message, Throwable cause )
    {
        super( message, cause );
    }
    

}