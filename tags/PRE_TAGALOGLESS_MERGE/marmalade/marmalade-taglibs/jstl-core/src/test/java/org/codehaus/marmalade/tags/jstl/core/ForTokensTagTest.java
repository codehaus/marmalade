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
/* Created on Apr 15, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.PassThroughExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;

/**
 * @author jdcasey
 */
public class ForTokensTagTest extends TestCase
{
    private void _testTokenIteration( String delim, String value,
        int expectedResult ) throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "items", value );
        attrs.addAttribute( "", "delims", delim );
        attrs.addAttribute( "", "var", "item" );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new PassThroughExpressionEvaluator(  ) );

        ForTokensTag tag = new ForTokensTag( ti );

        MarmaladeTagInfo counterTI = new MarmaladeTagInfo(  );

        counterTI.setAttributes( new DefaultRawAttributes(  ) );

        CounterTestTag counter = new CounterTestTag( counterTI );

        counter.setParent( tag );
        tag.addChild( counter );

        tag.execute( new DefaultContext(  ) );

        assertEquals( expectedResult, counter.counter(  ) );
    }

    public void testDoExecute_SingleDelim_SingleValue(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        _testTokenIteration( ",", "one", 1 );
    }

    public void testDoExecute_SingleDelim_MultiValue(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        _testTokenIteration( ",", "one,two", 2 );
    }

    public void testDoExecute_MultiDelim_SingleValue(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        _testTokenIteration( ",;", "one", 1 );
    }

    public void testDoExecute_MultiDelim_MultiValue(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        _testTokenIteration( ",;", "one,two;three", 3 );
    }
}
