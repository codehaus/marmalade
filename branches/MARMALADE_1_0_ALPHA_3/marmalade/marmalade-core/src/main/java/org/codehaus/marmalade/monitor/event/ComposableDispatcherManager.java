package org.codehaus.marmalade.monitor.event;

import org.codehaus.marmalade.monitor.event.context.ContextEventDispatcher;

/**
 * @author jdcasey
 */
public class ComposableDispatcherManager
    implements EventDispatcherManager
{
    
    private ContextEventDispatcher contextDispatcher;
    
    public void initContextDispatcher(ContextEventDispatcher contextDispatcher)
    {
        if(this.contextDispatcher == null)
        {
            this.contextDispatcher = contextDispatcher;
        }
    }

    public ContextEventDispatcher getContextDispatcher()
    {
        return contextDispatcher;
    }

}
