/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly;


/**
 * @author jdcasey
 */
public class JellyCompatUncheckedException extends RuntimeException
{
    public JellyCompatUncheckedException(  )
    {
    }

    public JellyCompatUncheckedException( String message )
    {
        super( message );
    }

    public JellyCompatUncheckedException( Throwable cause )
    {
        super( cause );
    }

    public JellyCompatUncheckedException( String message, Throwable cause )
    {
        super( message, cause );
    }
}
