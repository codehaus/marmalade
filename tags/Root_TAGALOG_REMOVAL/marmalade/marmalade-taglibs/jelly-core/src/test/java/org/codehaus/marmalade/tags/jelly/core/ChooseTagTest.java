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
public class ChooseTagTest extends TestCase
{
    public void testExecute_C1Fires(  )
        throws MarmaladeExecutionException
    {
        testChooseConfiguration( "true", "true", true, false, false );
    }

    public void testExecute_C2Fires(  )
        throws MarmaladeExecutionException
    {
        testChooseConfiguration( "false", "true", false, true, false );
    }

    public void testExecute_OFires(  )
        throws MarmaladeExecutionException
    {
        testChooseConfiguration( "false", "false", false, false, true );
    }

    private void testChooseConfiguration( String firstWhenTest,
        String secondWhenTest, boolean firstShouldSucceed,
        boolean secondShouldSucceed, boolean otherwiseShouldSucceed )
        throws MarmaladeExecutionException
    {
        ChooseTag tag = new ChooseTag( new MarmaladeTagInfo(  ) );

        DefaultRawAttributes attrs1 = new DefaultRawAttributes(  );

        attrs1.addAttribute( "", WhenTag.TEST_ATTRIBUTE, firstWhenTest );

        MarmaladeTagInfo mti1 = new MarmaladeTagInfo(  );

        mti1.setAttributes( attrs1 );
        mti1.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        WhenTag c1 = new WhenTag( mti1 );

        FlagChildTestTag c1f = new FlagChildTestTag( new MarmaladeTagInfo(  ) );

        c1.addChild( c1f );
        c1f.setParent( c1 );
        tag.addChild( c1 );
        c1.setParent( tag );

        DefaultRawAttributes attrs2 = new DefaultRawAttributes(  );

        attrs2.addAttribute( "", WhenTag.TEST_ATTRIBUTE, secondWhenTest );

        MarmaladeTagInfo mti2 = new MarmaladeTagInfo(  );

        mti2.setAttributes( attrs2 );
        mti2.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        WhenTag c2 = new WhenTag( mti2 );

        FlagChildTestTag c2f = new FlagChildTestTag( new MarmaladeTagInfo(  ) );

        c2.addChild( c2f );
        c2f.setParent( c2 );
        tag.addChild( c2 );
        c2.setParent( tag );

        OtherwiseTag o = new OtherwiseTag( new MarmaladeTagInfo(  ) );

        FlagChildTestTag of = new FlagChildTestTag( new MarmaladeTagInfo(  ) );

        o.addChild( of );
        of.setParent( o );
        tag.addChild( o );
        o.setParent( tag );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertTrue( "Condition 1 fired flag is wrong.",
            c1f.fired(  ) == firstShouldSucceed );
        assertTrue( "Condition 2 fired flag is wrong.",
            c2f.fired(  ) == secondShouldSucceed );
        assertTrue( "Otherwise fired flag is wrong.",
            of.fired(  ) == otherwiseShouldSucceed );
    }
}
