/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.model.beans;

import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

/**
 * @author jdcasey
 */
public class TestBeanWithProperty
{

    public static final String PROPERTY_VAR = "property";
    
    private String property;

    public TestBeanWithProperty(){
    }
    
    public void setProperty(String property) {
        this.property = property;
    }
    
    public void execute(MarmaladeExecutionContext context) {
        context.setVariable(PROPERTY_VAR, property);
    }
    
}
