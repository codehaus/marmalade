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
package org.codehaus.marmalade.tags.jelly.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class CatchTagTest extends TestCase
{
    public void testDoExecuteWithException_NoClass(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", CatchTag.VAR_ATTRIBUTE, "exception" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );

        CatchTag tag = new CatchTag( mti );

        ErrorGeneratingTestTag errTag = new ErrorGeneratingTestTag( new MarmaladeTagInfo(  ) );

        errTag.setParent( tag );
        tag.addChild( errTag );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
        }
        catch ( Throwable e )
        {
            e.printStackTrace(  );
            fail( "Exception should have been caught in tag." );
        }

        Throwable t = ( Throwable ) ctx.getVariable( "exception", null );

        assertNotNull( "Should have caught an exception", t );
        assertTrue( "Exception should be UnsupportedOperationException",
            t instanceof UnsupportedOperationException );
    }

    public void testDoExecuteWithException_WithClass_Match(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", CatchTag.VAR_ATTRIBUTE, "exception" );
        attrs.addAttribute( "", CatchTag.CLASS_ATTRIBUTE,
            "java.lang.UnsupportedOperationException" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        CatchTag tag = new CatchTag( mti );

        ErrorGeneratingTestTag errTag = new ErrorGeneratingTestTag( new MarmaladeTagInfo(  ) );

        errTag.setParent( tag );
        tag.addChild( errTag );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
        }
        catch ( Throwable e )
        {
            e.printStackTrace(  );
            fail( "Exception should have been caught in tag." );
        }

        Throwable t = ( Throwable ) ctx.getVariable( "exception", null );

        assertNotNull( "Should have caught an exception", t );
        assertTrue( "Exception should be UnsupportedOperationException",
            t instanceof UnsupportedOperationException );
    }

    public void testDoExecuteWithException_WithClass_NoMatch(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", CatchTag.VAR_ATTRIBUTE, "exception" );
        attrs.addAttribute( "", CatchTag.CLASS_ATTRIBUTE,
            "java.lang.IllegalArgumentException" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        CatchTag tag = new CatchTag( mti );

        ErrorGeneratingTestTag errTag = new ErrorGeneratingTestTag( new MarmaladeTagInfo(  ) );

        errTag.setParent( tag );
        tag.addChild( errTag );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( "Exception should NOT have been caught in tag." );
        }
        catch ( Throwable e )
        {
        }

        Throwable t = ( Throwable ) ctx.getVariable( "exception", null );

        assertNull( "Should have caught an exception", t );
    }
}
