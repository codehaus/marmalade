package org.codehaus.marmalade.monitor.log;

import java.util.List;

/**
 * @author jdcasey
 */
public interface MarmaladeLog
{
    
    void log(String level, CharSequence content);
    
    void log(String level, CharSequence content, Throwable error);
    
    void log(String level, Throwable error);
    
    void log(String level, List contentList);
    
}
