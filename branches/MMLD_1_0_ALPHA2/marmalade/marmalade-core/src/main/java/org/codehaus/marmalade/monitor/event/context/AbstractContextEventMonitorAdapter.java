package org.codehaus.marmalade.monitor.event.context;

import java.io.PrintWriter;
import java.io.Reader;

/**
 * @author jdcasey
 */
public abstract class AbstractContextEventMonitorAdapter
    implements ContextEventMonitor
{
    
    protected AbstractContextEventMonitorAdapter()
    {
    }

    public void variableSet( Object varKey, Object varValue )
    {
    }

    public void variableRemoved( Object varKey )
    {
    }

    public void scopeCreated()
    {
    }

    public void scopeRestored()
    {
    }

    public void outWriterChanged( PrintWriter old, PrintWriter replacement )
    {
    }

    public void errWriterChanged( PrintWriter old, PrintWriter replacement )
    {
    }

    public void inReaderChanged( Reader old, Reader replacement )
    {
    }

}
