package org.codehaus.marmalade.monitor.event.context;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author jdcasey
 */
public class DefaultContextEventDispatcher
    extends AbstractContextEventDispatcher
{
    
    private Set contextMonitors = new HashSet();

    protected Iterator contextMonitorIterator()
    {
        return contextMonitors.iterator();
    }

    public void addContextEventMonitor( ContextEventMonitor monitor )
    {
        contextMonitors.add(monitor);
    }

    public void removeContextEventMonitor( ContextEventMonitor monitor )
    {
        contextMonitors.remove(monitor);
    }

}
