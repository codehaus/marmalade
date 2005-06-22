/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.el.BareBonesExpressionEvaluator;
import org.codehaus.marmalade.lb.model.LateBoundTagFactory;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.parsing.ParserHint;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author jdcasey
 */
public class BeanTagTest
    extends MockObjectTestCase
{
    
    public void testShouldConstructWithNoParams() {
        BeanTag tag = new BeanTag();
    }
    
    public void testShouldRequireNameAttribute() throws MarmaladeExecutionException {
        Mock attrsMock = mock(MarmaladeAttributes.class);
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(DefinitionTagConstants.TAG_NAME_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue(null));
        
        BeanTag tag = new BeanTag();
        
        MarmaladeAttributes attrs = (MarmaladeAttributes)attrsMock.proxy();
        tag.setAttributes(attrs);
        tag.setExpressionEvaluator(new BareBonesExpressionEvaluator());
        tag.setTagInfo(new MarmaladeTagInfo());
        
        try
        {
            tag.execute(new DefaultContext());
            fail("should fail because of name attribute");
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing name attribute
        }
    }

    public void testShouldRequireBeanClassAttribute() throws MarmaladeExecutionException {
        Mock attrsMock = mock(MarmaladeAttributes.class);
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(DefinitionTagConstants.TAG_NAME_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue("test"));
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(BeanTag.BEAN_CLASS_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue(null));
        
        BeanTag tag = new BeanTag();
        
        MarmaladeAttributes attrs = (MarmaladeAttributes)attrsMock.proxy();
        tag.setAttributes(attrs);
        tag.setExpressionEvaluator(new BareBonesExpressionEvaluator());
        tag.setTagInfo(new MarmaladeTagInfo());
        
        try
        {
            tag.execute(new DefaultContext());
            fail("should fail because of class attribute");
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing class attribute
        }
    }

    public void testShouldRequireMethodAttribute() throws MarmaladeExecutionException {
        Mock attrsMock = mock(MarmaladeAttributes.class);
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(DefinitionTagConstants.TAG_NAME_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue("test"));
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(BeanTag.BEAN_CLASS_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue("org.codehaus.marmalade.lb.tags.TestTag"));
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(BeanTag.METHOD_NAME_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue(null));
        
        BeanTag tag = new BeanTag();
        
        MarmaladeAttributes attrs = (MarmaladeAttributes)attrsMock.proxy();
        tag.setAttributes(attrs);
        tag.setExpressionEvaluator(new BareBonesExpressionEvaluator());
        tag.setTagInfo(new MarmaladeTagInfo());
        
        try
        {
            tag.execute(new DefaultContext());
            fail("should fail because of method attribute");
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing method attribute
        }
    }

    public void testShouldAddLooseTagToLibrary() throws MarmaladeExecutionException {
        Mock attrsMock = mock(MarmaladeAttributes.class);
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(DefinitionTagConstants.TAG_NAME_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue("test"));
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(BeanTag.METHOD_NAME_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue("execute"));
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(BeanTag.BEAN_CLASS_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue("org.codehaus.marmalade.lb.tags.TestTag"));
        
        BeanTag tag = new BeanTag();
        
        MarmaladeAttributes attrs = (MarmaladeAttributes)attrsMock.proxy();
        tag.setAttributes(attrs);
        tag.setExpressionEvaluator(new BareBonesExpressionEvaluator());
        tag.setTagInfo(new MarmaladeTagInfo());
        
        Mock parentMock = mock(LateBoundTagLibraryOwner.class);
        parentMock.expects(once()).method("registerTag").with(isA(String.class), isA(ParserHint.class), isA(LateBoundTagFactory.class)).isVoid();
        
        LateBoundTagLibraryOwner parent = (LateBoundTagLibraryOwner)parentMock.proxy();
        tag.setParent(parent);
        
        tag.execute(new DefaultContext());
    }

}
