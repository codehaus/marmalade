/* Created on Jun 25, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class ParseTagTest extends TestCase {
    
    private static final String SCRIPT_WITHOUT_XML_DECL = 
        "<set xmlns=\"jelly:core\" var=\"testKey\" value=\"testVal\"/>";
    
    private static final String SCRIPT_WITH_XML_DECL = "<?xml version=\"1.0\"?>\n" +
            SCRIPT_WITHOUT_XML_DECL;

    public void testShouldStoreScriptWithValidXMLFromTextAttributeWhenVarAttributeSpecified() throws MarmaladeExecutionException {
        DefaultRawAttributes attrs = new DefaultRawAttributes();
        attrs.addAttribute("", ParseTag.TEXT_ATTRIBUTE, SCRIPT_WITH_XML_DECL);
        attrs.addAttribute("", ParseTag.VAR_ATTRIBUTE, "var");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attrs);
        
        ParseTag tag = new ParseTag(mti);
        
        DefaultContext ctx = new DefaultContext();
        tag.execute(ctx);
        
        MarmaladeScript script = (MarmaladeScript)ctx.getVariable("var", null);
        assertNotNull(script);
    }

    public void testShouldStoreScriptWithInvalidXMLFromTextAttributeWhenVarAttributeSpecified() throws MarmaladeExecutionException {
        DefaultRawAttributes attrs = new DefaultRawAttributes();
        attrs.addAttribute("", ParseTag.TEXT_ATTRIBUTE, SCRIPT_WITHOUT_XML_DECL);
        attrs.addAttribute("", ParseTag.VAR_ATTRIBUTE, "var");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attrs);
        
        ParseTag tag = new ParseTag(mti);
        
        DefaultContext ctx = new DefaultContext();
        tag.execute(ctx);
        
        MarmaladeScript script = (MarmaladeScript)ctx.getVariable("var", null);
        assertNotNull(script);
    }

    public void testShouldStoreScriptWithValidXMLFromBodyWhenVarAttributeSpecified() throws MarmaladeExecutionException {
        DefaultRawAttributes attrs = new DefaultRawAttributes();
        attrs.addAttribute("", ParseTag.VAR_ATTRIBUTE, "var");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attrs);
        mti.appendText(SCRIPT_WITH_XML_DECL.toCharArray(), 0, SCRIPT_WITH_XML_DECL.length());
        
        ParseTag tag = new ParseTag(mti);
        
        DefaultContext ctx = new DefaultContext();
        tag.execute(ctx);
        
        MarmaladeScript script = (MarmaladeScript)ctx.getVariable("var", null);
        assertNotNull(script);
    }

    public void testShouldStoreScriptWithInvalidXMLFromBodyWhenVarAttributeSpecified() throws MarmaladeExecutionException {
        DefaultRawAttributes attrs = new DefaultRawAttributes();
        attrs.addAttribute("", ParseTag.VAR_ATTRIBUTE, "var");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attrs);
        mti.appendText(SCRIPT_WITHOUT_XML_DECL.toCharArray(), 0, SCRIPT_WITHOUT_XML_DECL.length());
        
        ParseTag tag = new ParseTag(mti);
        
        DefaultContext ctx = new DefaultContext();
        tag.execute(ctx);
        
        MarmaladeScript script = (MarmaladeScript)ctx.getVariable("var", null);
        assertNotNull(script);
    }

    public void testShouldExecuteScriptWithValidXMLFromBodyWhenVarAttributeNotSpecified() throws MarmaladeExecutionException {
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(new DefaultRawAttributes());
        mti.appendText(SCRIPT_WITH_XML_DECL.toCharArray(), 0, SCRIPT_WITH_XML_DECL.length());
        
        ParseTag tag = new ParseTag(mti);
        
        DefaultContext ctx = new DefaultContext();
        tag.execute(ctx);
        
        String value = (String)ctx.getVariable("testKey", null);
        assertEquals("testVal", value);
    }

    public void testShouldFailWhenBodyEmptyAndTextAttributeNotSpecified() throws MarmaladeExecutionException {
        DefaultRawAttributes attrs = new DefaultRawAttributes();
        attrs.addAttribute("", ParseTag.VAR_ATTRIBUTE, "var");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attrs);
        
        ParseTag tag = new ParseTag(mti);
        
        try {
            tag.execute(new DefaultContext());
            fail("Should fail because of missing script (text attribute and body are empty).");
        } catch (MarmaladeExecutionException e) {
            // should fail b/c no script was provided for parsing.
        }
    }

}
