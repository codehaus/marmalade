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
/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.tags.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;

/**
 * @author jdcasey
 */
public class SetTagTest
    extends TestCase
{
    public void testShouldRequireVarAttribute() throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo();
        DefaultRawAttributes attrs = new DefaultRawAttributes();

        attrs.addAttribute( "", "", "value", "test" );

        SetTag tag = new SetTag();

        tag.setTagInfo( ti );
        tag.setAttributes( new DefaultAttributes( attrs ) );

        try
        {
            tag.execute( new DefaultContext() );
            fail( "should fail because of missing var attribute" );
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing var attribute.
        }
    }

    public void testShouldRequireValueAttributeWithMissingBody() throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo();
        DefaultRawAttributes attrs = new DefaultRawAttributes();

        attrs.addAttribute( "", "", "var", "test" );

        SetTag tag = new SetTag();

        tag.setTagInfo( ti );
        tag.setAttributes( new DefaultAttributes( attrs ) );

        try
        {
            tag.execute( new DefaultContext() );
            fail( "should fail because of missing value attribute and body" );
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing var attribute and body.
        }
    }

    public void testShouldNotRequireValueAttributeWithSpecifiedBody() throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo();
        DefaultRawAttributes attrs = new DefaultRawAttributes();

        attrs.addAttribute( "", "", "var", "test" );

        SetTag tag = new SetTag();

        tag.setTagInfo( ti );
        tag.setAttributes( new DefaultAttributes( attrs ) );
        tag.appendBodyText( "value" );

        tag.execute( new DefaultContext() );
    }

    public void testShouldSetContextVariableWithVarAndValueAttributes() throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes();

        attrs.addAttribute( "", "", "var", "testKey" );
        attrs.addAttribute( "", "", "value", "value" );

        MarmaladeTagInfo ti = new MarmaladeTagInfo();

        SetTag tag = new SetTag();

        tag.setTagInfo( ti );
        tag.setAttributes( new DefaultAttributes( attrs ) );

        DefaultContext ctx = new DefaultContext();

        assertNull( "Variable \'testKey\' should not be in context.", ctx.getVariable( "testKey", null ) );
        tag.execute( ctx );
        assertEquals( "Can't verify successful setVariable() operation.", "value", ctx.getVariable( "testKey", null ) );
    }

    public void testShouldSetTargetPropertyWithTargetPropertyAndValueAttributes() throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes();

        // We won't jump into this, but *real* EL's should support it, in
        // theory...
        // attrs.addAttribute( "", "", "property", "attribute.value" );
        attrs.addAttribute( "", "", "property", "attribute" );
        attrs.addAttribute( "", "", "target", "${subject}" );
        attrs.addAttribute( "", "", "value", "testResult" );

        MarmaladeTagInfo ti = new MarmaladeTagInfo();

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        SetTag tag = new SetTag();

        tag.setTagInfo( ti );
        tag.setAttributes( tagAttrs );

        DefaultContext ctx = new DefaultContext();

        TestSubject subject = new TestSubject( "testSource" );

        ctx.setVariable( "subject", subject );

        assertEquals( "Pre-Test: subject's attribute's value should be \'testSource\'", "testSource", subject
            .getAttribute() );
        tag.execute( ctx );
        assertEquals( "Post-Test: subject's attribute's value should be \'testResult\'", "testResult", subject
            .getAttribute() );
    }

    public void testShouldThrowExceptionWhenTargetPropertyWithInvalidSimplePropertyNameAndBareBonesEvaluator()
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes();

        // We won't jump into this, but *real* EL's should support it, in
        // theory...
        attrs.addAttribute( "", "", "property", "attribute.value" );
        attrs.addAttribute( "", "", "target", "${subject}" );
        attrs.addAttribute( "", "", "value", "testResult" );

        MarmaladeTagInfo ti = new MarmaladeTagInfo();

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        SetTag tag = new SetTag();

        tag.setTagInfo( ti );
        tag.setAttributes( tagAttrs );

        DefaultContext ctx = new DefaultContext();

        TestSubject subject = new TestSubject( "testSource" );

        ctx.setVariable( "subject", subject );

        try
        {
            tag.execute( ctx );
            fail( "Should fail because of invalid property name" );
        }
        catch ( ExpressionEvaluationException e )
        {
            // should snag on invalid property name
        }
    }
}