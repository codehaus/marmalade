package org.codehaus.marmalade.reader;

/**
 * @author jdcasey
 */
public class ScriptReadException
    extends Exception
{

    public ScriptReadException()
    {
        super();
    }

    public ScriptReadException( String message )
    {
        super( message );
    }

    public ScriptReadException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public ScriptReadException( Throwable cause )
    {
        super( cause );
    }
}