/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.IllegalScriptStructureException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author jdcasey
 */
public class PropertyTagTest
    extends MockObjectTestCase
{
    
    public void testShouldConstructWithNoArgs() {
        PropertyTag tag = new PropertyTag();
    }

    public void testShouldRequireNameAttribute() throws MarmaladeExecutionException {
        PropertyTag tag = new PropertyTag();
        
        Mock attrsMock = mock(MarmaladeAttributes.class);
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(PropertyTag.NAME_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue(null));
        
        MarmaladeAttributes attrs = (MarmaladeAttributes)attrsMock.proxy();
        tag.setAttributes(attrs);
        tag.setTagInfo(new MarmaladeTagInfo());
        
        try
        {
            tag.execute(new DefaultContext());
            fail("should fail on missing name attribute");
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing name attribute
        }
    }

    public void testShouldRequirePropertyOwnerParent() throws MarmaladeExecutionException {
        PropertyTag tag = new PropertyTag();
        
        Mock attrsMock = mock(MarmaladeAttributes.class);
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(PropertyTag.NAME_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue("test"));
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(AbstractValueTag.VALUE_ATTRIBUTE), eq(Object.class), ANYTHING).will(returnValue("value"));
        
        MarmaladeAttributes attrs = (MarmaladeAttributes)attrsMock.proxy();
        tag.setAttributes(attrs);
        tag.setTagInfo(new MarmaladeTagInfo());
        
        try
        {
            tag.execute(new DefaultContext());
            fail("should fail on missing PropertyOwner parent");
        }
        catch ( IllegalScriptStructureException e )
        {
            // should snag on missing PropertyOwner parent
        }
    }

    public void testShouldSetPropertyOnParent() throws MarmaladeExecutionException {
        PropertyTag tag = new PropertyTag();
        
        Mock attrsMock = mock(MarmaladeAttributes.class);
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(PropertyTag.NAME_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue("test"));
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(AbstractValueTag.VALUE_ATTRIBUTE), eq(Object.class), ANYTHING).will(returnValue("value"));
        
        MarmaladeAttributes attrs = (MarmaladeAttributes)attrsMock.proxy();
        tag.setAttributes(attrs);
        tag.setTagInfo(new MarmaladeTagInfo());
        
        Mock parentMock = mock(PropertyOwner.class);
        parentMock.expects(once()).method("addProperty").with(eq("test"), eq("value")).isVoid();
        
        PropertyOwner parent = (PropertyOwner)parentMock.proxy();
        
        tag.setParent(parent);
        
        tag.execute(new DefaultContext());
    }
}
