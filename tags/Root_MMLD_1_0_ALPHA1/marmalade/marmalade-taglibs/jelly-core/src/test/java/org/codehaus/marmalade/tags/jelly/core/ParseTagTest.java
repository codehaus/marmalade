/*
 *
 * Copyright (c) 2004 John Dennis Casey
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */
/* Created on Jun 25, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class ParseTagTest extends TestCase
{
    private static final String SCRIPT_WITHOUT_XML_DECL = "<set xmlns=\"jelly:core\" var=\"testKey\" value=\"testVal\"/>";
    private static final String SCRIPT_WITH_XML_DECL = "<?xml version=\"1.0\"?>\n"
        + SCRIPT_WITHOUT_XML_DECL;

    public void testShouldStoreScriptWithValidXMLFromTextAttributeWhenVarAttributeSpecified(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", ParseTag.TEXT_ATTRIBUTE,
            SCRIPT_WITH_XML_DECL );
        attrs.addAttribute( "", "", ParseTag.VAR_ATTRIBUTE, "var" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        tagAttrs.setExpressionEvaluator( el );

        ParseTag tag = new ParseTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );
        tag.setExpressionEvaluator( el );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        MarmaladeScript script = ( MarmaladeScript ) ctx.getVariable( "var",
                null );

        assertNotNull( script );
    }

    public void testShouldStoreScriptWithInvalidXMLFromTextAttributeWhenVarAttributeSpecified(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", ParseTag.TEXT_ATTRIBUTE,
            SCRIPT_WITHOUT_XML_DECL );
        attrs.addAttribute( "", "", ParseTag.VAR_ATTRIBUTE, "var" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        tagAttrs.setExpressionEvaluator( el );

        ParseTag tag = new ParseTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );
        tag.setExpressionEvaluator( el );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        MarmaladeScript script = ( MarmaladeScript ) ctx.getVariable( "var",
                null );

        assertNotNull( script );
    }

    public void testShouldStoreScriptWithValidXMLFromBodyWhenVarAttributeSpecified(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", ParseTag.VAR_ATTRIBUTE, "var" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        tagAttrs.setExpressionEvaluator( el );

        ParseTag tag = new ParseTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );
        tag.setExpressionEvaluator( el );
        tag.appendBodyText( SCRIPT_WITH_XML_DECL );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        MarmaladeScript script = ( MarmaladeScript ) ctx.getVariable( "var",
                null );

        assertNotNull( script );
    }

    public void testShouldStoreScriptWithInvalidXMLFromBodyWhenVarAttributeSpecified(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", ParseTag.VAR_ATTRIBUTE, "var" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        tagAttrs.setExpressionEvaluator( el );

        ParseTag tag = new ParseTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );
        tag.setExpressionEvaluator( el );
        tag.appendBodyText( SCRIPT_WITHOUT_XML_DECL );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        MarmaladeScript script = ( MarmaladeScript ) ctx.getVariable( "var",
                null );

        assertNotNull( script );
    }

    public void testShouldExecuteScriptWithValidXMLFromBodyWhenVarAttributeNotSpecified(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        ParseTag tag = new ParseTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( new DefaultAttributes(  ) );
        tag.appendBodyText( SCRIPT_WITH_XML_DECL );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        String value = ( String ) ctx.getVariable( "testKey", null );

        assertEquals( "testVal", value );
    }

    public void testShouldFailWhenBodyEmptyAndTextAttributeNotSpecified(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", ParseTag.VAR_ATTRIBUTE, "var" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        tagAttrs.setExpressionEvaluator( el );

        ParseTag tag = new ParseTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );
        tag.setExpressionEvaluator( el );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( 
                "Should fail because of missing script (text attribute and body are empty)." );
        }
        catch ( MarmaladeExecutionException e )
        {
            // should fail b/c no script was provided for parsing.
        }
    }
}
