/* Created on Aug 10, 2004 */
package org.codehaus.marmalade.instrumentation;

import org.codehaus.marmalade.MarmaladeException;
import java.io.PrintWriter;
import java.io.PrintStream;


/**
 * @author jdcasey
 */
public aspect ResourceBundleErrorMessageInjector extends ErrorEnhancer {

    Object around(MarmaladeException ex): getAnyMessage(ex)
    {
        String result = ErrorUtil.getMessage(getOriginalMessage(ex));
        
        return result;
    }
    
}
