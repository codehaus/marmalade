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
package org.codehaus.marmalade.tags.jelly.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class SetTagTest extends TestCase
{
    public void testDoExecute_VarValue(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", SetTag.VAR_ATTRIBUTE, "testKey" );
        attrs.addAttribute( "", "", SetTag.VALUE_ATTRIBUTE, "value" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        tagAttrs.setExpressionEvaluator( el );

        SetTag tag = new SetTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );
        tag.setExpressionEvaluator( el );

        DefaultContext ctx = new DefaultContext(  );

        assertNull( "Variable \'testKey\' should not be in context.",
            ctx.getVariable( "testKey", null ) );
        tag.execute( ctx );
        assertEquals( "Can't verify successful setVariable() operation.",
            "value", ctx.getVariable( "testKey", null ) );
    }

    public void testDoExecute_PropertyTargetValue(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", SetTag.PROPERTY_ATTRIBUTE, "attribute.value" );
        attrs.addAttribute( "", "", SetTag.TARGET_ATTRIBUTE, "#subject" );
        attrs.addAttribute( "", "", SetTag.VALUE_ATTRIBUTE, "testResult" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        tagAttrs.setExpressionEvaluator( el );

        SetTag tag = new SetTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );
        tag.setExpressionEvaluator( el );

        DefaultContext ctx = new DefaultContext(  );

        TestSubject subject = new TestSubject( new TestAttribute( "testSource" ) );

        ctx.setVariable( "subject", subject );

        assertEquals( "Pre-Test: subject's attribute's value should be \'testSource\'",
            "testSource", subject.getAttribute(  ).getValue(  ) );
        tag.execute( ctx );
        assertEquals( "Post-Test: subject's attribute's value should be \'testResult\'",
            "testResult", subject.getAttribute(  ).getValue(  ) );
    }
}
