/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.el.BareBonesExpressionEvaluator;
import org.codehaus.marmalade.lb.discovery.LateBindingTagLibraryResolver;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author jdcasey
 */
public class LateBoundTagLibraryTagTest
    extends MockObjectTestCase
{

    public void testShouldConstructWithResolver() {
        LateBoundTagLibraryOwner tag = new LateBoundTagLibraryTag(new LateBindingTagLibraryResolver());
    }
    
    public void testShouldRequireTaglibAttribute() throws MarmaladeExecutionException {
        LateBoundTagLibraryTag tag = new LateBoundTagLibraryTag(new LateBindingTagLibraryResolver());

        Mock attributesMock = mock(MarmaladeAttributes.class);
        attributesMock.expects(atLeastOnce()).method("getValue").with(eq(LateBoundTagLibraryTag.TAGLIB_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue(null));
        attributesMock.expects(atLeastOnce()).method("getValue").with(eq(LateBoundTagLibraryTag.SCHEME_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue("lb"));
        
        MarmaladeAttributes attrs = (MarmaladeAttributes)attributesMock.proxy();
        tag.setAttributes(attrs);
        tag.setExpressionEvaluator(new BareBonesExpressionEvaluator());
        tag.setTagInfo(new MarmaladeTagInfo());
        
        MarmaladeExecutionContext ctx = new DefaultContext();
        
        try
        {
            tag.execute(ctx);
            fail("should fail because of missing taglib attribute");
        }
        catch ( MissingAttributeException e )
        {
        }
    }
    
    public void testShouldRequirePrefixAttribute() throws MarmaladeExecutionException {
        LateBoundTagLibraryTag tag = new LateBoundTagLibraryTag(new LateBindingTagLibraryResolver());

        Mock attributesMock = mock(MarmaladeAttributes.class);
        attributesMock.expects(atLeastOnce()).method("getValue").with(eq(LateBoundTagLibraryTag.SCHEME_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue(null));
//        attributesMock.expects(atLeastOnce()).method("getValue").with(eq(LateBoundTagLibraryTag.TAGLIB_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue("test"));
        
        MarmaladeAttributes attrs = (MarmaladeAttributes)attributesMock.proxy();
        tag.setAttributes(attrs);
        tag.setExpressionEvaluator(new BareBonesExpressionEvaluator());
        tag.setTagInfo(new MarmaladeTagInfo());
        
        MarmaladeExecutionContext ctx = new DefaultContext();
        
        try
        {
            tag.execute(ctx);
            fail("should fail because of missing scheme attribute");
        }
        catch ( MissingAttributeException e )
        {
        }
    }
    
}
