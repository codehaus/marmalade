package org.codehaus.marmalade.monitor.event.context;

/**
 * @author jdcasey
 */
public interface ContextEventDispatcher extends ContextEventMonitor
{
    
    void addContextEventMonitor(ContextEventMonitor monitor);
    
    void removeContextEventMonitor(ContextEventMonitor monitor);

}
