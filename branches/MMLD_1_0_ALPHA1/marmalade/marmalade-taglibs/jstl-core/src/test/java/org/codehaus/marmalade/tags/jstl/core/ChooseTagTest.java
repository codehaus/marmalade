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
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class ChooseTagTest extends TestCase
{
    public void testShouldExecuteFirstWhenChildWithTrueTestResultAndNoOthers_TTX(  )
        throws MarmaladeExecutionException
    {
        runTestScenario( "true", "true", true, false, false );
    }

    public void testShouldExecuteSecondWhenChildWithTrueTestResultAndNoOthers_FTX(  )
        throws MarmaladeExecutionException
    {
        runTestScenario( "false", "true", false, true, false );
    }

    public void testShouldExecuteOtherwiseIfNoWhenChildTestResultIsTrue(  )
        throws MarmaladeExecutionException
    {
        runTestScenario( "false", "false", false, false, true );
    }

    public void testShouldFirstWhenChildWithTrueTestResultAndNoOthers_FF(  )
        throws MarmaladeExecutionException
    {
        runTestScenario( "true", "false", true, false, false );
    }

    private void runTestScenario( String condOneTestVal, String condTwoTestVal,
        boolean condOneShouldFire, boolean condTwoShouldFire,
        boolean otherShouldFire )
        throws MarmaladeExecutionException
    {
        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator(  );

        MarmaladeTagInfo chooseTI = new MarmaladeTagInfo(  );
        ChooseTag tag = new ChooseTag(  );

        tag.setTagInfo( chooseTI );

        MarmaladeTagInfo c1TI = new MarmaladeTagInfo(  );

        DefaultRawAttributes c1Attrs = new DefaultRawAttributes(  );

        c1Attrs.addAttribute( "", "", WhenTag.TEST_ATTRIBUTE, condOneTestVal );

        DefaultAttributes c1TagAttrs = new DefaultAttributes( c1Attrs );

        c1TagAttrs.setExpressionEvaluator( el );

        WhenTag c1 = new WhenTag(  );

        c1.setTagInfo( c1TI );
        c1.setAttributes( c1TagAttrs );
        c1.setExpressionEvaluator( el );

        MarmaladeTagInfo c1fTI = new MarmaladeTagInfo(  );

        FlagChildTestTag c1f = new FlagChildTestTag(  );

        c1f.setTagInfo( c1fTI );

        c1.addChild( c1f );
        c1f.setParent( c1 );
        tag.addChild( c1 );
        c1.setParent( tag );

        MarmaladeTagInfo c2TI = new MarmaladeTagInfo(  );
        DefaultRawAttributes c2Attrs = new DefaultRawAttributes(  );

        c2Attrs.addAttribute( "", "", WhenTag.TEST_ATTRIBUTE, condTwoTestVal );

        DefaultAttributes c2TagAttrs = new DefaultAttributes( c2Attrs );

        c2TagAttrs.setExpressionEvaluator( el );

        WhenTag c2 = new WhenTag(  );

        c2.setTagInfo( c2TI );
        c2.setAttributes( c2TagAttrs );
        c2.setExpressionEvaluator( el );

        MarmaladeTagInfo c2fTI = new MarmaladeTagInfo(  );

        FlagChildTestTag c2f = new FlagChildTestTag(  );

        c2f.setTagInfo( c2fTI );

        c2.addChild( c2f );
        c2f.setParent( c2 );
        tag.addChild( c2 );
        c2.setParent( tag );

        MarmaladeTagInfo oTI = new MarmaladeTagInfo(  );

        OtherwiseTag o = new OtherwiseTag(  );

        o.setTagInfo( oTI );

        MarmaladeTagInfo ofTI = new MarmaladeTagInfo(  );

        FlagChildTestTag of = new FlagChildTestTag(  );

        of.setTagInfo( ofTI );

        o.addChild( of );
        of.setParent( o );
        tag.addChild( o );
        o.setParent( tag );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertEquals( "Condition 1 firing status is wrong.", condOneShouldFire,
            c1f.fired(  ) );
        assertEquals( "Condition 2 firing status is wrong.", condTwoShouldFire,
            c2f.fired(  ) );
        assertEquals( "Otherwise firing status is wrong.", otherShouldFire,
            of.fired(  ) );
    }
}
