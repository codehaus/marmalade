/* Created on Jul 13, 2004 */
package org.codehaus.marmalade.compat.jelly.model;

import org.apache.commons.jelly.Tag;
import org.apache.commons.jelly.test.MarmaladeTagWrapperTestTag;
import org.codehaus.marmalade.compat.jelly.metamodel.JellyCompatMarmaladeTaglib;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.el.commonsEl.CommonsElExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class JellyCompatMarmaladeTaglibTest extends TestCase {
    
    public void testShouldResolveTestTaglibFromProperties() throws MarmaladeExecutionException {
        JellyCompatMarmaladeTaglib taglib = new JellyCompatMarmaladeTaglib();
        MarmaladeTagInfo ti = new MarmaladeTagInfo();
        ti.setTaglib("test");
        ti.setElement("test");
        
        JellyCompatMarmaladeTag tag = (JellyCompatMarmaladeTag) taglib.createTag(ti);
        
        tag.setTagInfo(ti);
        tag.setAttributes(new DefaultAttributes());
        
        DefaultContext ctx = new DefaultContext();
        tag.execute(ctx);
        
        ExpressionEvaluator el = new CommonsElExpressionEvaluator();
        
        Tag jellyTag = (Tag)ctx.getVariable("thisTag", el);
        assertNotNull(jellyTag);
        assertEquals(MarmaladeTagWrapperTestTag.class, jellyTag.getClass());
    }

}
