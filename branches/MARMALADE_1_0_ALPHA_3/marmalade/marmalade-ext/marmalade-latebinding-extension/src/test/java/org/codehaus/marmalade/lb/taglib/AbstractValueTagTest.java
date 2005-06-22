/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.model.MarmaladeControlDefinitions;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

/**
 * @author jdcasey
 */
public class AbstractValueTagTest
    extends MockObjectTestCase
{

    public void testRetrieveValueFromAttribute() throws MarmaladeExecutionException {
        MarmaladeTag tag = new AbstractValueTag() {
            public void doExecute(MarmaladeExecutionContext context)
            throws MarmaladeExecutionException
            {
                context.setVariable("var", extractValue(context));
            }
        };
        
        Mock attrsMock = mock(MarmaladeAttributes.class);
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(AbstractValueTag.VALUE_ATTRIBUTE), eq(Object.class), ANYTHING).will(returnValue("test"));
        
        MarmaladeAttributes attrs = (MarmaladeAttributes)attrsMock.proxy();
        tag.setAttributes(attrs);
        tag.setTagInfo(new MarmaladeTagInfo());
        
        MarmaladeExecutionContext context = new DefaultContext();
        tag.execute(context);
        
        assertEquals("value not set", "test", context.getVariable("var", null));
    }
    
    public void testRetrieveValueFromBody() throws MarmaladeExecutionException {
        MarmaladeTag tag = new AbstractValueTag() {
            public void doExecute(MarmaladeExecutionContext context)
            throws MarmaladeExecutionException
            {
                context.setVariable("var", extractValue(context));
            }
        };
        
        Mock attrsMock = mock(MarmaladeAttributes.class);
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(AbstractValueTag.VALUE_ATTRIBUTE), eq(Object.class), ANYTHING).will(returnValue(null));
        
        Constraint[] constraints = {
            eq(MarmaladeControlDefinitions.MARMALADE_RESERVED_NS), 
            eq(MarmaladeControlDefinitions.PRESERVE_BODY_WHITESPACE_ATTRIBUTE), 
            eq(Boolean.class),
            ANYTHING,
            eq(true)
        };
        
        attrsMock.expects(atLeastOnce()).method("getValue").with(constraints).will(returnValue(true));
        
        MarmaladeAttributes attrs = (MarmaladeAttributes)attrsMock.proxy();
        tag.setAttributes(attrs);
        tag.setTagInfo(new MarmaladeTagInfo());
        tag.appendBodyText("test");
        
        MarmaladeExecutionContext context = new DefaultContext();
        tag.execute(context);
        
        assertEquals("value not set", "test", context.getVariable("var", null));
    }
    
}
