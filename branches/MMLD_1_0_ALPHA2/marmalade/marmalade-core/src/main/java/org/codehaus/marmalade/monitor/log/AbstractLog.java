package org.codehaus.marmalade.monitor.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

/**
 * @author jdcasey
 */
public abstract class AbstractLog
    implements MarmaladeLog
{

    protected abstract boolean enabled( String level );

    protected abstract void doLog( String level, String content );

    public void log( String level, CharSequence content )
    {
        if ( enabled( level ) )
        {
            doLog( level, content.toString() );
        }
    }

    public void log( String level, CharSequence content, Throwable error )
    {
        if ( enabled( level ) )
        {
            doLog( level, content.toString() + "\n" + formatThrowable( error ) );
        }
    }

    public void log( String level, Throwable error )
    {
        if ( enabled( level ) )
        {
            doLog( level, formatThrowable( error ) );
        }
    }

    public void log( String level, List contentList )
    {
        if ( enabled( level ) )
        {
            StringBuffer messageBuffer = new StringBuffer();

            for ( Iterator it = contentList.iterator(); it.hasNext(); )
            {
                Object element = (Object) it.next();

                if ( element instanceof CharSequence )
                {
                    messageBuffer.append( ((CharSequence) element).toString() );
                }
                else if ( element instanceof LogRenderable )
                {
                    messageBuffer.append( ((LogRenderable) element).render() );
                }
                else if ( element instanceof Throwable )
                {
                    messageBuffer.append( '\n' ).append( formatThrowable( (Throwable) element ) );
                }
                else
                {
                    messageBuffer.append( String.valueOf( element ) );
                }
            }

            doLog( level, messageBuffer.toString() );
        }
    }

    protected String formatThrowable( Throwable error )
    {
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter( sWriter );

        error.printStackTrace( pWriter );

        return sWriter.toString();
    }

}