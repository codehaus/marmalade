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
/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author jdcasey
 */
public class ErrTagTest extends TestCase
{
    public void testShouldFailWithMissingValueAndDefaultAttributesAndMissingBody(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( new DefaultRawAttributes(  ) );

        ErrTag tag = new ErrTag( ti );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( 
                "should fail because of missing value attribute (default attribute and body are missing also)" );
        }
        catch ( MarmaladeExecutionException e )
        {
            // should snag on missing value attribute when default attribute and body are missing
        }
    }

    public void testShouldSucceedWithSpecifiedValueAttribute(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "value", "message" ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );

        ErrTag tag = new ErrTag( ti );

        tag.execute( new DefaultContext(  ) );
    }

    public void testShouldSucceedWithSpecifiedBody(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( new DefaultRawAttributes(  ) );
        ti.appendText( "test".toCharArray(  ), 0, "test".length(  ) );

        ErrTag tag = new ErrTag( ti );

        tag.execute( new DefaultContext(  ) );
    }

    public void testShouldSucceedWithSpecifiedDefaultAttribute(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "default", "message" ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );

        ErrTag tag = new ErrTag( ti );

        tag.execute( new DefaultContext(  ) );
    }

    public void testShouldReproduceBodyToErrWriterBeforeEvaluatingValueOrDefaultAttributes(  )
        throws MarmaladeExecutionException
    {
        String message = "test message";
        String message2 = "test message 2";
        String message3 = "test message 3";

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "value", message2 ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "default", message3 ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.appendText( message.toCharArray(  ), 0, message.length(  ) );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        StringWriter err = new StringWriter(  );

        ErrTag tag = new ErrTag( ti );

        DefaultContext context = new DefaultContext(  );

        context.setErrWriter( new PrintWriter( err ) );
        tag.execute( context );

        assertEquals( message, err.getBuffer(  ).toString(  ) );
    }

    public void testShouldReproduceValueAttributeResultToErrWriterBeforeEvaluatingDefaultAttribute(  )
        throws MarmaladeExecutionException
    {
        String message = "test message";
        String message2 = "test message 2";

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "value", message ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "default", message2 ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        StringWriter err = new StringWriter(  );

        ErrTag tag = new ErrTag( ti );

        DefaultContext context = new DefaultContext(  );

        context.setErrWriter( new PrintWriter( err ) );
        tag.execute( context );

        assertEquals( message, err.getBuffer(  ).toString(  ) );
    }

    public void testShouldProperlyEscapeXmlWhenEnabled(  )
        throws MarmaladeExecutionException
    {
        String message = "<test attr=\"message\"/>";
        String escapedMessage = "&lt;test attr=\"message\"/&gt;";

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "value", message ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "escapeXml", "true" ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        StringWriter err = new StringWriter(  );

        ErrTag tag = new ErrTag( ti );

        DefaultContext context = new DefaultContext(  );

        context.setErrWriter( new PrintWriter( err ) );
        tag.execute( context );

        assertEquals( escapedMessage, err.getBuffer(  ).toString(  ) );
    }
}