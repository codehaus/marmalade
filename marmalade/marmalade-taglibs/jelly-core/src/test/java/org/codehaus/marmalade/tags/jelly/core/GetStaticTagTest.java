/* Created on Jun 23, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class GetStaticTagTest extends TestCase {
    
    public static final String TEST_FIELD = "test";

    public void testShouldRequireClassNameAttribute() throws MarmaladeExecutionException {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", GetStaticTag.FIELD_ATTRIBUTE, "FIELD_NAME");
        attributes.addAttribute("", GetStaticTag.VAR_ATTRIBUTE, "var");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        GetStaticTag tag = new GetStaticTag(mti);
        
        try {
            tag.execute(new DefaultContext());
            fail("Should fail with missing className attribute.");
        }
        catch(MissingAttributeException e) {
            // should snag on missing className attribute.
        }
    }

    public void testShouldRequireFieldAttribute() throws MarmaladeExecutionException {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", GetStaticTag.CLASS_NAME_ATTRIBUTE, getClass().getName());
        attributes.addAttribute("", GetStaticTag.VAR_ATTRIBUTE, "var");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        GetStaticTag tag = new GetStaticTag(mti);
        
        try {
            tag.execute(new DefaultContext());
            fail("Should fail with missing field attribute.");
        }
        catch(MissingAttributeException e) {
            // should snag on missing field attribute.
        }
    }

    public void testShouldRequireVarAttribute() throws MarmaladeExecutionException {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", GetStaticTag.FIELD_ATTRIBUTE, "FIELD_NAME");
        attributes.addAttribute("", GetStaticTag.CLASS_NAME_ATTRIBUTE, getClass().getName());
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        GetStaticTag tag = new GetStaticTag(mti);
        
        try {
            tag.execute(new DefaultContext());
            fail("Should fail with missing var attribute.");
        }
        catch(MissingAttributeException e) {
            // should snag on missing var attribute.
        }
    }

    public void testShouldFailWithBadClassName() {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", GetStaticTag.CLASS_NAME_ATTRIBUTE, "org.somewhere.BadClass");
        attributes.addAttribute("", GetStaticTag.FIELD_ATTRIBUTE, "FIELD_NAME");
        attributes.addAttribute("", GetStaticTag.VAR_ATTRIBUTE, "var");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        GetStaticTag tag = new GetStaticTag(mti);
        
        try {
            tag.execute(new DefaultContext());
            fail("Should fail with invalid className specification.");
        }
        catch(MarmaladeExecutionException e) {
            // should snag on invalid className spec.
        }
    }

    public void testShouldFailWithBadField() throws MarmaladeExecutionException {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", GetStaticTag.CLASS_NAME_ATTRIBUTE, getClass().getName());
        attributes.addAttribute("", GetStaticTag.FIELD_ATTRIBUTE, "FIELD_NAME");
        attributes.addAttribute("", GetStaticTag.VAR_ATTRIBUTE, "var");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        GetStaticTag tag = new GetStaticTag(mti);
        
        try {
            tag.execute(new DefaultContext());
            fail("Should fail with invalid field specification.");
        }
        catch(MarmaladeExecutionException e) {
            // should snag on invalid field spec.
        }
    }

    public void testShouldSucceedWithValidClassNameFieldAndVar() throws MarmaladeExecutionException {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", GetStaticTag.CLASS_NAME_ATTRIBUTE, getClass().getName());
        attributes.addAttribute("", GetStaticTag.FIELD_ATTRIBUTE, "TEST_FIELD");
        attributes.addAttribute("", GetStaticTag.VAR_ATTRIBUTE, "var");
        
        OgnlExpressionEvaluator ognlEL = new OgnlExpressionEvaluator();
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(ognlEL);
        
        GetStaticTag tag = new GetStaticTag(mti);
        
        DefaultContext context = new DefaultContext();
        tag.execute(context);
        
        assertEquals(TEST_FIELD, context.getVariable("var", ognlEL));
    }

}
