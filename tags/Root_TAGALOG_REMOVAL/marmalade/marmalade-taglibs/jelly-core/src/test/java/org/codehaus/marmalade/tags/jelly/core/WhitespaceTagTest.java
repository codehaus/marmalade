/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jelly.AbstractJellyMarmaladeTag;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class WhitespaceTagTest extends TestCase {

    public void testCheckControlPreserveWhitespace() throws MarmaladeExecutionException {
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        
        String testBody = "This is a \ntest.";
        mti.appendText(testBody.toCharArray(), 0, testBody.length());
        
        WhitespaceCheckerTag tag = new WhitespaceCheckerTag(mti);
        tag.execute(new DefaultContext());
        
        assertEquals("Whitespace should be preserved by default.", testBody, tag.getMyBody());
    }
    
    public void testCheckControlTrimWhitespace() throws MarmaladeExecutionException {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", AbstractJellyMarmaladeTag.TRIM_ATTRIBUTE, "true");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        String testBody = "This is a \ntest.";
        mti.appendText(testBody.toCharArray(), 0, testBody.length());
        
        WhitespaceCheckerTag tag = new WhitespaceCheckerTag(mti);
        tag.execute(new DefaultContext());
        
        String checkBody = "This is a test.";
        
        assertEquals("Whitespace should not be preserved when trim attribute is specified.", checkBody, tag.getMyBody());
    }
    
    public void testShouldEnforceWhitespacePreservation() throws MarmaladeExecutionException {
        
        WhitespaceTag tag = new WhitespaceTag(new MarmaladeTagInfo());
        
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        String testBody = "This is a \ntest.";
        mti.appendText(testBody.toCharArray(), 0, testBody.length());
        
        WhitespaceCheckerTag child = new WhitespaceCheckerTag(mti);
        
        child.setParent(tag);
        tag.addChild(child);
        
        tag.execute(new DefaultContext());
        
        assertEquals(testBody, child.getMyBody());
    }
    
    public void testShouldEnforceWhitespacePreservationWhenChildSpecifiesTrim() throws MarmaladeExecutionException {
        
        WhitespaceTag tag = new WhitespaceTag(new MarmaladeTagInfo());
        
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", AbstractJellyMarmaladeTag.TRIM_ATTRIBUTE, "true");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        String testBody = "This is a \ntest.";
        mti.appendText(testBody.toCharArray(), 0, testBody.length());
        
        WhitespaceCheckerTag child = new WhitespaceCheckerTag(mti);
        
        child.setParent(tag);
        tag.addChild(child);
        
        tag.execute(new DefaultContext());
        
        assertEquals(testBody, child.getMyBody());
    }
    
}
