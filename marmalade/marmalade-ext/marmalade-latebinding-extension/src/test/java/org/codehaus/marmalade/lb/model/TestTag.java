/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.model;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;

/**
 * @author jdcasey
 */
public class TestTag
    extends AbstractMarmaladeTag
{

    private String strArg;
    private Integer intArg;
    private String strProp;
    private Integer intProp;

    public TestTag()
    {
    }
    
    public TestTag(String strArg, Integer intArg) {
        this.strArg = strArg;
        this.intArg = intArg;
    }
    
    public void setStringProperty(String strProp) {
        this.strProp = strProp;
    }
    
    public void setIntegerProperty(Integer intProp) {
        this.intProp = intProp;
    }
    
    public String getStringProperty() {
        return strProp;
    }
    
    public Integer getIntegerProperty() {
        return intProp;
    }
    
    public String getStringArg() {
        return strArg;
    }
    
    public Integer getIntegerArg() {
        return intArg;
    }

}
