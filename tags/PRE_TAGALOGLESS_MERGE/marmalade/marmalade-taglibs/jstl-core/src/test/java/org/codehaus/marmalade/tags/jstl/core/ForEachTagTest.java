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
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jdcasey
 */
public class ForEachTagTest extends TestCase
{
    public void testShouldIterateOverSimpleArrayOfValues(  )
        throws MarmaladeExecutionException
    {
        _testSimpleIteration( new String[] { "one", "two", "three" }, 3 );
    }

    public void testShouldIterateOverSimpleCollectionOfValues(  )
        throws MarmaladeExecutionException
    {
        List items = new ArrayList(  );

        items.add( "one" );
        items.add( "two" );
        items.add( "three" );

        _testSimpleIteration( items, 3 );
    }

    public void testShouldIterateOverTokensExtractedFromSimpleString(  )
        throws MarmaladeExecutionException
    {
        _testSimpleIteration( "one,two,three", 3 );
    }

    public void testShouldIterateOverSingleNonStringObject(  )
        throws MarmaladeExecutionException
    {
        _testSimpleIteration( new Integer( 1 ), 1 );
    }

    public void testShouldIterateOverArrayStartingAtNonZeroBeginIndex(  )
        throws MarmaladeExecutionException
    {
        _testNonZeroBeginIteration( new String[] { "one", "two", "three" }, 1, 2 );
    }

    public void testShouldIterateOverCollectionStartingAtNonZeroBeginIndex(  )
        throws MarmaladeExecutionException
    {
        List items = new ArrayList(  );

        items.add( "one" );
        items.add( "two" );
        items.add( "three" );

        _testNonZeroBeginIteration( items, 1, 2 );
    }

    public void testShouldIterateOverStringTokensStartingAtNonZeroBeginIndex(  )
        throws MarmaladeExecutionException
    {
        _testNonZeroBeginIteration( "one,two,three", 1, 2 );
    }

    public void testShouldNotIterateOverSingleObjectWhenStartingAtNonZeroBeginIndex(  )
        throws MarmaladeExecutionException
    {
        _testNonZeroBeginIteration( new Integer( 1 ), 1, 0 );
    }

    public void testShouldIterateOverArrayEndingAtPositiveEndIndex(  )
        throws MarmaladeExecutionException
    {
        _testPositiveEndIteration( new String[] { "one", "two", "three" }, 1, 2 );
    }

    public void testShouldIterateOverCollectionEndingAtPositiveEndIndex(  )
        throws MarmaladeExecutionException
    {
        List items = new ArrayList(  );

        items.add( "one" );
        items.add( "two" );
        items.add( "three" );

        _testPositiveEndIteration( items, 1, 2 );
    }

    public void testShouldIterateOverStringTokensEndingAtPositiveEndIndex(  )
        throws MarmaladeExecutionException
    {
        _testPositiveEndIteration( "one,two,three", 1, 2 );
    }

    public void testShouldIterateOverArrayEndingAtZeroEndIndex(  )
        throws MarmaladeExecutionException
    {
        _testPositiveEndIteration( new String[] { "one", "two", "three" }, 0, 1 );
    }

    public void testShouldIterateOverCollectionEndingAtZeroEndIndex(  )
        throws MarmaladeExecutionException
    {
        List items = new ArrayList(  );

        items.add( "one" );
        items.add( "two" );
        items.add( "three" );

        _testPositiveEndIteration( items, 0, 1 );
    }

    public void testShouldIterateOverStringTokensEndingAtZeroEndIndex(  )
        throws MarmaladeExecutionException
    {
        _testPositiveEndIteration( "one,two,three", 0, 1 );
    }

    public void testShouldIterateOverSingleObjectEndingAtZeroEndIndex(  )
        throws MarmaladeExecutionException
    {
        _testPositiveEndIteration( new Integer( 1 ), 0, 1 );
    }

    public void testShouldIterateOverSingleObjectWhenEndingAtPositiveEndIndex(  )
        throws MarmaladeExecutionException
    {
        _testPositiveEndIteration( new Integer( 1 ), 1, 1 );
    }

    public void testShouldIterateOverArrayUsingNonOneStepping(  )
        throws MarmaladeExecutionException
    {
        _testMultiStepIteration( new String[] { "one", "two", "three" }, 2, 2 );
    }

    public void testShouldIterateOverCollectionUsingNonOneStepping(  )
        throws MarmaladeExecutionException
    {
        List items = new ArrayList(  );

        items.add( "one" );
        items.add( "two" );
        items.add( "three" );

        _testMultiStepIteration( items, 2, 2 );
    }

    public void testShouldIterateOverStringTokensUsingNonOneStepping(  )
        throws MarmaladeExecutionException
    {
        _testMultiStepIteration( "one,two,three", 2, 2 );
    }

    public void testShouldIterateOverSingleObjectUsingNonOneStepping(  )
        throws MarmaladeExecutionException
    {
        _testMultiStepIteration( new Integer( 1 ), 2, 1 );
    }

    private void _testSimpleIteration( Object iterationSubject,
        int expectedResult ) throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "items", "#items" );
        attrs.addAttribute( "", "var", "item" );

        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator(  );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( el );

        ForEachTag tag = new ForEachTag( ti );

        MarmaladeTagInfo counterTI = new MarmaladeTagInfo(  );

        counterTI.setAttributes( new DefaultRawAttributes(  ) );

        CounterTestTag counter = new CounterTestTag( counterTI );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", iterationSubject );

        tag.execute( ctx );

        assertNull( ctx.getVariable( "item", el ) );
        assertEquals( expectedResult, counter.counter(  ) );
    }

    private void _testNonZeroBeginIteration( Object iterationSubject,
        int beginIdx, int expectedResult )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "items", "#items" );
        attrs.addAttribute( "", "var", "item" );
        attrs.addAttribute( "", "begin", "" + beginIdx );

        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator(  );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( el );

        ForEachTag tag = new ForEachTag( ti );

        MarmaladeTagInfo counterTI = new MarmaladeTagInfo(  );

        counterTI.setAttributes( new DefaultRawAttributes(  ) );

        CounterTestTag counter = new CounterTestTag( counterTI );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", iterationSubject );

        tag.execute( ctx );

        assertNull( ctx.getVariable( "item", el ) );
        assertEquals( expectedResult, counter.counter(  ) );
    }

    private void _testPositiveEndIteration( Object iterationSubject,
        int endIdx, int expectedResult )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "items", "#items" );
        attrs.addAttribute( "", "var", "item" );
        attrs.addAttribute( "", "end", "" + endIdx );

        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator(  );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( el );

        ForEachTag tag = new ForEachTag( ti );

        MarmaladeTagInfo counterTI = new MarmaladeTagInfo(  );

        counterTI.setAttributes( new DefaultRawAttributes(  ) );

        CounterTestTag counter = new CounterTestTag( counterTI );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", iterationSubject );

        tag.execute( ctx );

        assertNull( ctx.getVariable( "item", el ) );
        assertEquals( expectedResult, counter.counter(  ) );
    }

    private void _testMultiStepIteration( Object iterationSubject, int step,
        int expectedResult ) throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "items", "#items" );
        attrs.addAttribute( "", "var", "item" );
        attrs.addAttribute( "", "step", "" + step );

        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator(  );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( el );

        ForEachTag tag = new ForEachTag( ti );

        MarmaladeTagInfo counterTI = new MarmaladeTagInfo(  );

        counterTI.setAttributes( new DefaultRawAttributes(  ) );

        CounterTestTag counter = new CounterTestTag( counterTI );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", iterationSubject );

        tag.execute( ctx );

        assertNull( ctx.getVariable( "item", el ) );
        assertEquals( expectedResult, counter.counter(  ) );
    }
}
