/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.model.beans;

/**
 * @author jdcasey
 */
public class TestBeanWithExecEmpty
{

    private boolean didExecute;

    public TestBeanWithExecEmpty()
    {
    }
    
    public boolean didExecute() {
        return didExecute;
    }
    
    public void execute() {
        didExecute = true;
    }

}
