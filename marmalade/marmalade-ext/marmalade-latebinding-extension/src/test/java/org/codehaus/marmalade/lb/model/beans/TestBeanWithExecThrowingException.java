/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.model.beans;

import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

/**
 * @author jdcasey
 */
public class TestBeanWithExecThrowingException
{

    private boolean didExecute;

    public TestBeanWithExecThrowingException()
    {
    }
    
    public boolean didExecute() {
        return didExecute;
    }
    
    public void execute() throws TestException {
        didExecute = true;
        throw new TestException();
    }

}
