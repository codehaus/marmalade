/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author jdcasey
 */
public class ConstructorArgTagTest
    extends MockObjectTestCase
{
    
    public void testShouldConstructWithNoArgs() {
        ConstructorArgTag tag = new ConstructorArgTag();
    }

    public void testShouldSetPropertyOnParent() throws MarmaladeExecutionException {
        ConstructorArgTag tag = new ConstructorArgTag();
        
        Mock attrsMock = mock(MarmaladeAttributes.class);
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(AbstractValueTag.VALUE_ATTRIBUTE), eq(Object.class), ANYTHING).will(returnValue("value"));
        
        MarmaladeAttributes attrs = (MarmaladeAttributes)attrsMock.proxy();
        tag.setAttributes(attrs);
        tag.setTagInfo(new MarmaladeTagInfo());
        
        Mock parentMock = mock(ConstructorArgOwner.class);
        parentMock.expects(once()).method("addConstructorArg").with(eq("value")).isVoid();
        
        ConstructorArgOwner parent = (ConstructorArgOwner)parentMock.proxy();
        
        tag.setParent(parent);
        
        tag.execute(new DefaultContext());
    }
}
