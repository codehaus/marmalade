/* Created on Jun 25, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class IncludeTagTest extends TestCase {
    
    private static final String SCRIPT_WITHOUT_XML_DECL = 
        "<set xmlns=\"jelly:core\" var=\"testKey\" value=\"testVal\"/>";
    
    private static final String SCRIPT_WITH_XML_DECL = "<?xml version=\"1.0\"?>\n" +
            SCRIPT_WITHOUT_XML_DECL;

    private static final String SCRIPT_WITHOUT_XML_WITH_EXPECTATION_DECL = 
        "<?xml version=\"1.0\"?>\n" +
        "<set xmlns=\"jelly:core\" xmlns:marmalade=\"marmalade\" marmalade:el=\"ognl\" var=\"testKey\" value=\"#testVal\"/>";

    public void testShouldSkipIfTestAttributeEvaluatesToFalse() throws MarmaladeExecutionException {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );
        attrs.addAttribute("", IncludeTag.TEST_ATTRIBUTE, "false");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );
        mti.setAttributes(attrs);
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );
        
        IncludeTag tag = new IncludeTag( mti );
        
        tag.execute(new DefaultContext());
    }

    public void testShouldFailIfNoScriptSourceFromFileUriOrBody() {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );
        attrs.addAttribute("", IncludeTag.TEST_ATTRIBUTE, "true");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );
        mti.setAttributes(attrs);
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );
        
        IncludeTag tag = new IncludeTag( mti );
        
        try {
            tag.execute(new DefaultContext());
            fail("Should fail if no script source (file, uri, body()) provided.");
        }
        catch(MarmaladeExecutionException e) {
            // should snag on missing inclusion script.
        }
    }

    public void testShouldExportVariableWithValidXMLFromBodyWhenExportAttributeSpecified() throws MarmaladeExecutionException {
        DefaultRawAttributes attrs = new DefaultRawAttributes();
        attrs.addAttribute("", IncludeTag.EXPORT_ATTRIBUTE, "true");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attrs);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        mti.appendText(SCRIPT_WITH_XML_DECL.toCharArray(), 0, SCRIPT_WITH_XML_DECL.length());
        
        ParseTag tag = new ParseTag(mti);
        
        DefaultContext ctx = new DefaultContext();
        tag.execute(ctx);
        
        String value = (String)ctx.getVariable("testKey", null);
        assertEquals("testVal", value);
    }

    public void testShouldExportVariableWithValidXMLFromBodyWhenInheritingValuesAndExportAttributeSpecified() throws MarmaladeExecutionException {
        DefaultRawAttributes attrs = new DefaultRawAttributes();
        attrs.addAttribute("", IncludeTag.EXPORT_ATTRIBUTE, "true");
        attrs.addAttribute("", IncludeTag.INHERIT_ATTRIBUTE, "true");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attrs);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        mti.appendText(SCRIPT_WITHOUT_XML_WITH_EXPECTATION_DECL.toCharArray(), 0, SCRIPT_WITHOUT_XML_WITH_EXPECTATION_DECL.length());
        
        ParseTag tag = new ParseTag(mti);
        
        DefaultContext ctx = new DefaultContext();
        ctx.setVariable("testVal", "myVal");
        tag.execute(ctx);
        
        String value = (String)ctx.getVariable("testKey", null);
        assertEquals("myVal", value);
    }

    public void testShouldNotExportVariableWithValidXMLFromBodyWhenExportAttributeUnspecified() throws MarmaladeExecutionException {
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.appendText(SCRIPT_WITH_XML_DECL.toCharArray(), 0, SCRIPT_WITH_XML_DECL.length());
        
        ParseTag tag = new ParseTag(mti);
        
        DefaultContext ctx = new DefaultContext();
        tag.execute(ctx);
        
        String value = (String)ctx.getVariable("testKey", null);
        assertEquals("testVal", value);
    }
    
}
