/* Created on Jul 13, 2004 */
package org.codehaus.marmalade.compat.jelly.model;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.XMLOutput;
import org.apache.commons.jelly.test.MarmaladeTagWrapperTestTag;
import org.apache.commons.jelly.test.MarmaladeTaglibWrapperCompatTestTagLibrary;
import org.codehaus.marmalade.compat.jelly.runtime.MarmaladeCompatJellyContext;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.el.commonsEl.CommonsElExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.test.tags.TestTag;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class MarmaladeCompatBodyScriptTest extends TestCase {
    
    public void testShouldConcatenateStringChildren() throws JellyTagException {
        String firstComponent = "This is a";
        String secondComponent = "test";
        String check = firstComponent + secondComponent;
        
        MarmaladeTaglibWrapperCompatTestTagLibrary taglib = new MarmaladeTaglibWrapperCompatTestTagLibrary();
        
        DefaultContext ctx = new DefaultContext();
        ExpressionEvaluator el = new CommonsElExpressionEvaluator();
        
        List bodyItems = new ArrayList();
        
        bodyItems.add(firstComponent);
        bodyItems.add(secondComponent);
        
        JellyCompatMarmaladeTag owner = new JellyCompatMarmaladeTag(taglib);
        MarmaladeTagInfo ti = new MarmaladeTagInfo();
        ti.setElement("test");
        owner.setAttributes(new DefaultAttributes());
        
        MarmaladeCompatBodyScript script = new MarmaladeCompatBodyScript(owner, ctx, bodyItems);
        
        StringWriter writer = new StringWriter();
        XMLOutput output = XMLOutput.createXMLOutput(writer);
        MarmaladeCompatJellyContext jellyCtx = new MarmaladeCompatJellyContext(ctx, el);
        
        script.run(jellyCtx, output);
        
        assertEquals(check, writer.toString());
    }

    public void testShouldExecuteMarmaladeTagChildren() throws JellyTagException {
        MarmaladeTaglibWrapperCompatTestTagLibrary taglib = new MarmaladeTaglibWrapperCompatTestTagLibrary();
        
        TestTag tag = new TestTag();
        
        DefaultContext ctx = new DefaultContext();
        ExpressionEvaluator el = new CommonsElExpressionEvaluator();
        
        List bodyItems = new ArrayList();
        bodyItems.add(tag);
        
        JellyCompatMarmaladeTag owner = new JellyCompatMarmaladeTag(taglib);
        MarmaladeTagInfo ti = new MarmaladeTagInfo();
        ti.setElement("test");
        owner.setAttributes(new DefaultAttributes());
        
        MarmaladeCompatBodyScript script = new MarmaladeCompatBodyScript(owner, ctx, bodyItems);
        
        StringWriter writer = new StringWriter();
        XMLOutput output = XMLOutput.createXMLOutput(writer);
        MarmaladeCompatJellyContext jellyCtx = new MarmaladeCompatJellyContext(ctx, el);
        
        script.run(jellyCtx, output);
        
        assertTrue(tag.tagFired());
    }

}
