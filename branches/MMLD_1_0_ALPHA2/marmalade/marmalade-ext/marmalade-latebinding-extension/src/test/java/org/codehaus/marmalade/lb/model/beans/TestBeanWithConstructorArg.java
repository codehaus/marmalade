/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.model.beans;

import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

/**
 * @author jdcasey
 */
public class TestBeanWithConstructorArg
{
    
    public static final String ARG_VAR = "arg";
    
    private String arg;
    
    public TestBeanWithConstructorArg(String arg){
        this.arg = arg;
    }
    
    public void execute(MarmaladeExecutionContext context) {
        context.setVariable(ARG_VAR, arg);
    }
    
}
