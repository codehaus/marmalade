package org.codehaus.marmalade.monitor.event;

import org.codehaus.marmalade.monitor.event.context.ContextEventDispatcher;

/**
 * @author jdcasey
 */
public interface EventDispatcherManager
{
    
    ContextEventDispatcher getContextDispatcher();

}
