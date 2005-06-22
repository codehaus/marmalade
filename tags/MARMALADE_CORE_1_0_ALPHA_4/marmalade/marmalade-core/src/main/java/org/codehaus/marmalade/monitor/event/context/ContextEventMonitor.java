package org.codehaus.marmalade.monitor.event.context;

import java.io.PrintWriter;
import java.io.Reader;

/**
 * @author jdcasey
 */
public interface ContextEventMonitor
{
    
    void variableSet(Object varKey, Object varValue, boolean externalizable);
    
    void variableRemoved(Object varKey);
    
    void scopeCreated();
    
    void scopeRestored();
    
    void outWriterChanged(PrintWriter old, PrintWriter replacement);

    void errWriterChanged(PrintWriter old, PrintWriter replacement);

    void inReaderChanged(Reader old, Reader replacement);

}
