/* Created on Jul 13, 2004 */
package org.codehaus.marmalade.compat.jelly.model;

import org.apache.commons.jelly.TagLibrary;
import org.apache.commons.jelly.test.MarmaladeTaglibWrapperCompatTestTagLibrary;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.el.commonsEl.CommonsElExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class JellyCompatMarmaladeTagTest extends TestCase {
    
    public void testShouldConstructWithTagLibrary() {
        JellyCompatMarmaladeTaglib taglib = new JellyCompatMarmaladeTaglib();
        JellyCompatMarmaladeTag tag = new JellyCompatMarmaladeTag(taglib);
    }
    
    public void testShouldSetJellyTagAttribute() throws MarmaladeExecutionException {
        JellyCompatMarmaladeTaglib taglib = new JellyCompatMarmaladeTaglib();
        JellyCompatMarmaladeTag tag = new JellyCompatMarmaladeTag(taglib);
        
        MarmaladeTagInfo ti = new MarmaladeTagInfo();
        ti.setElement("test");
        ti.setTaglib("test");
        
        DefaultRawAttributes attrs = new DefaultRawAttributes();
        attrs.addAttribute("", "", "attribute", "value");
        
        ExpressionEvaluator el = new CommonsElExpressionEvaluator();
        tag.setExpressionEvaluator(el);
        
        tag.setTagInfo(ti);
        tag.setAttributes(new DefaultAttributes(attrs));
        
        DefaultContext ctx = new DefaultContext();
        tag.execute(ctx);
        
        assertEquals(Boolean.TRUE, ctx.getVariable("doTagRan", el));
        assertEquals(Boolean.TRUE, ctx.getVariable("attributeWasSet", el));
    }

    public void testShouldExecuteParentBodyRelationship() throws MarmaladeExecutionException {
        JellyCompatMarmaladeTaglib taglib = new JellyCompatMarmaladeTaglib();
        ExpressionEvaluator el = new CommonsElExpressionEvaluator();
        
        JellyCompatMarmaladeTag parent = new JellyCompatMarmaladeTag(taglib);
        
        MarmaladeTagInfo pti = new MarmaladeTagInfo();
        pti.setElement("parentTest");
        pti.setTaglib("test");
        
        parent.setTagInfo(pti);
        parent.setAttributes(new DefaultAttributes());
        parent.setExpressionEvaluator(el);
        
        JellyCompatMarmaladeTag tag = new JellyCompatMarmaladeTag(taglib);
        
        MarmaladeTagInfo ti = new MarmaladeTagInfo();
        ti.setElement("test");
        ti.setTaglib("test");
        
        tag.setExpressionEvaluator(el);
        tag.setTagInfo(ti);
        tag.setAttributes(new DefaultAttributes());
        tag.setParent(parent);
        
        parent.addChild(tag);
        
        DefaultContext ctx = new DefaultContext();
        parent.execute(ctx);
        
        assertEquals(Boolean.TRUE, ctx.getVariable("parentExists", el));
    }

}
