package org.codehaus.marmalade.monitor.event.context;

import java.io.PrintWriter;
import java.io.Reader;
import java.util.Iterator;

/**
 * @author jdcasey
 */
public abstract class AbstractContextEventDispatcher
    implements ContextEventDispatcher
{
    
    protected AbstractContextEventDispatcher()
    {
    }
    
    protected abstract Iterator contextMonitorIterator();

    public void variableSet( Object varKey, Object varValue, boolean externalizable )
    {
        for ( Iterator it = contextMonitorIterator(); it.hasNext(); )
        {
            ContextEventMonitor monitor = (ContextEventMonitor) it.next();
            monitor.variableSet(varKey, varValue, externalizable);
        }
    }

    public void variableRemoved( Object varKey )
    {
        for ( Iterator it = contextMonitorIterator(); it.hasNext(); )
        {
            ContextEventMonitor monitor = (ContextEventMonitor) it.next();
            monitor.variableRemoved(varKey);
        }
    }

    public void scopeCreated()
    {
        for ( Iterator it = contextMonitorIterator(); it.hasNext(); )
        {
            ContextEventMonitor monitor = (ContextEventMonitor) it.next();
            monitor.scopeCreated();
        }
    }

    public void scopeRestored()
    {
        for ( Iterator it = contextMonitorIterator(); it.hasNext(); )
        {
            ContextEventMonitor monitor = (ContextEventMonitor) it.next();
            monitor.scopeCreated();
        }
    }

    public void outWriterChanged( PrintWriter old, PrintWriter replacement )
    {
        for ( Iterator it = contextMonitorIterator(); it.hasNext(); )
        {
            ContextEventMonitor monitor = (ContextEventMonitor) it.next();
            monitor.outWriterChanged(old, replacement);
        }
    }

    public void errWriterChanged( PrintWriter old, PrintWriter replacement )
    {
        for ( Iterator it = contextMonitorIterator(); it.hasNext(); )
        {
            ContextEventMonitor monitor = (ContextEventMonitor) it.next();
            monitor.errWriterChanged(old, replacement);
        }
    }

    public void inReaderChanged( Reader old, Reader replacement )
    {
        for ( Iterator it = contextMonitorIterator(); it.hasNext(); )
        {
            ContextEventMonitor monitor = (ContextEventMonitor) it.next();
            monitor.inReaderChanged(old, replacement);
        }
    }

}
