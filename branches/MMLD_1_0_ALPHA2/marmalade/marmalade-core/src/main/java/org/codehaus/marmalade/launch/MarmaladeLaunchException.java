package org.codehaus.marmalade.launch;

/**
 * @author jdcasey
 */
public class MarmaladeLaunchException
    extends Exception
{

    public MarmaladeLaunchException( String message )
    {
        super( message );
    }

    public MarmaladeLaunchException( String message, Throwable cause )
    {
        super( message, cause );
    }
}