/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.model.beans;

import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

/**
 * @author jdcasey
 */
public class TestBeanWithExecCtx
{

    private boolean didExecute;

    public TestBeanWithExecCtx()
    {
    }
    
    public boolean didExecute() {
        return didExecute;
    }
    
    public void execute(MarmaladeExecutionContext context) {
        didExecute = true;
    }

}
