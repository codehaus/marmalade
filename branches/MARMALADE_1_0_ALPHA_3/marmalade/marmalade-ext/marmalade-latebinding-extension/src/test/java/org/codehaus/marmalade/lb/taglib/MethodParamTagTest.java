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
public class MethodParamTagTest
    extends MockObjectTestCase
{
    
    public void testShouldConstructWithNoArgs() {
        MethodParamTag tag = new MethodParamTag();
    }

    public void testShouldSetPropertyOnParent() throws MarmaladeExecutionException {
        MethodParamTag tag = new MethodParamTag();
        
        Mock attrsMock = mock(MarmaladeAttributes.class);
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(AbstractValueTag.VALUE_ATTRIBUTE), eq(Object.class), ANYTHING).will(returnValue("value"));
        
        MarmaladeAttributes attrs = (MarmaladeAttributes)attrsMock.proxy();
        tag.setAttributes(attrs);
        tag.setTagInfo(new MarmaladeTagInfo());
        
        Mock parentMock = mock(MethodParamOwner.class);
        parentMock.expects(once()).method("addMethodParameter").with(eq("value")).isVoid();
        
        MethodParamOwner parent = (MethodParamOwner)parentMock.proxy();
        
        tag.setParent(parent);
        
        tag.execute(new DefaultContext());
    }
}
