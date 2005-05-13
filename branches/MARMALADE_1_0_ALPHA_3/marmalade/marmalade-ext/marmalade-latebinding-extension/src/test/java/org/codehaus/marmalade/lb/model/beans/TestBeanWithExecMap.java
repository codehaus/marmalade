/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.model.beans;

import java.util.Map;

/**
 * @author jdcasey
 */
public class TestBeanWithExecMap
{

    private boolean didExecute;

    public TestBeanWithExecMap()
    {
    }
    
    public boolean didExecute() {
        return didExecute;
    }
    
    public void execute(Map context) {
        didExecute = true;
    }

}
