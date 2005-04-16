package org.codehaus.marmalade.monitor.log;

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
            doLog( level, content.toString() + "\n" + LogSupport.formatThrowable( error ).toString() );
        }
    }

    public void log( String level, Throwable error )
    {
        if ( enabled( level ) )
        {
            doLog( level, LogSupport.formatThrowable( error ).toString() );
        }
    }

    public void log( String level, List contentList )
    {
        if ( enabled( level ) )
        {
            doLog( level, LogSupport.formatEntryList( contentList ).toString() );
        }
    }
}