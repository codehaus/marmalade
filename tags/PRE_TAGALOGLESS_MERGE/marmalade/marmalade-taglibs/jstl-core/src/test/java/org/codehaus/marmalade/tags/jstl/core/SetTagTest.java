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
package org.codehaus.marmalade.tags.jstl.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;

/**
 * @author jdcasey
 */
public class SetTagTest extends TestCase
{
    public void testShouldRequireVarAttribute(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "value", "test" ) );
        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        SetTag tag = new SetTag( ti );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "should fail because of missing var attribute" );
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing var attribute.
        }
    }

    public void testShouldRequireValueAttributeWithMissingBody(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "var", "test" ) );
        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        SetTag tag = new SetTag( ti );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "should fail because of missing value attribute and body" );
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing var attribute and body.
        }
    }

    public void testShouldNotRequireValueAttributeWithSpecifiedBody(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "var", "test" ) );
        ti.setAttributes( attrs );
        ti.appendText( "value".toCharArray(  ), 0, "value".length(  ) );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        SetTag tag = new SetTag( ti );

        tag.execute( new DefaultContext(  ) );
    }

    public void testShouldSetContextVariableWithVarAndValueAttributes(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "var", "testKey" ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "value", "value" ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        SetTag tag = new SetTag( ti );

        DefaultContext ctx = new DefaultContext(  );

        assertNull( "Variable \'testKey\' should not be in context.",
            ctx.getVariable( "testKey", null ) );
        tag.execute( ctx );
        assertEquals( "Can't verify successful setVariable() operation.",
            "value", ctx.getVariable( "testKey", null ) );
    }

    public void testShouldSetTargetPropertyWithTargetPropertyAndValueAttributes(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "property",
                "attribute.value" ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "target", "#subject" ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "value", "testResult" ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        SetTag tag = new SetTag( ti );

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
