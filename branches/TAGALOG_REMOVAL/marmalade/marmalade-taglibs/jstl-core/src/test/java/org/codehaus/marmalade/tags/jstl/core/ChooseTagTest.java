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
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;

/**
 * @author jdcasey
 */
public class ChooseTagTest extends TestCase
{
    public void testShouldExecuteFirstWhenChildWithTrueTestResultAndNoOthers_TTX(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        MarmaladeTagInfo chooseTI = new MarmaladeTagInfo(  );
        DefaultRawAttributes chooseTIAttrs = new DefaultRawAttributes(  );

        chooseTI.setAttributes( chooseTIAttrs );
        chooseTI.setElement( "choose" );
        chooseTI.setScheme( "marmalade" );
        chooseTI.setTaglib( "jstl-core" );

        ChooseTag tag = new ChooseTag( chooseTI );

        MarmaladeTagInfo c1TI = new MarmaladeTagInfo(  );
        DefaultRawAttributes c1TIAttrs = new DefaultRawAttributes(  );

        c1TIAttrs.addAttribute( new DefaultRawAttribute( "", "test", "true" ) );
        c1TI.setAttributes( c1TIAttrs );
        c1TI.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        WhenTag c1 = new WhenTag( c1TI );

        MarmaladeTagInfo c1fTI = new MarmaladeTagInfo(  );

        c1fTI.setAttributes( new DefaultRawAttributes(  ) );

        FlagChildTestTag c1f = new FlagChildTestTag( c1fTI );

        c1.addChild( c1f );
        c1f.setParent( c1 );
        tag.addChild( c1 );
        c1.setParent( tag );

        MarmaladeTagInfo c2TI = new MarmaladeTagInfo(  );
        DefaultRawAttributes c2TIAttrs = new DefaultRawAttributes(  );

        c2TIAttrs.addAttribute( new DefaultRawAttribute( "", "test", "true" ) );
        c2TI.setAttributes( c2TIAttrs );
        c2TI.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        WhenTag c2 = new WhenTag( c2TI );

        MarmaladeTagInfo c2fTI = new MarmaladeTagInfo(  );

        c2fTI.setAttributes( new DefaultRawAttributes(  ) );

        FlagChildTestTag c2f = new FlagChildTestTag( c2fTI );

        c2.addChild( c2f );
        c2f.setParent( c2 );
        tag.addChild( c2 );
        c2.setParent( tag );

        MarmaladeTagInfo oTI = new MarmaladeTagInfo(  );

        oTI.setAttributes( new DefaultRawAttributes(  ) );

        OtherwiseTag o = new OtherwiseTag( oTI );

        MarmaladeTagInfo ofTI = new MarmaladeTagInfo(  );

        ofTI.setAttributes( new DefaultRawAttributes(  ) );

        FlagChildTestTag of = new FlagChildTestTag( ofTI );

        o.addChild( of );
        of.setParent( o );
        tag.addChild( o );
        o.setParent( tag );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertTrue( "Condition 1 should have fired.", c1f.fired(  ) );
        assertFalse( "Condition 2 should NOT have fired.", c2f.fired(  ) );
        assertFalse( "Otherwise should NOT have fired.", of.fired(  ) );
    }

    public void testShouldExecuteFirstWhenChildWithTrueTestResultAndNoOthers_FTX(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        MarmaladeTagInfo chooseTI = new MarmaladeTagInfo(  );
        DefaultRawAttributes chooseTIAttrs = new DefaultRawAttributes(  );

        chooseTI.setAttributes( chooseTIAttrs );

        ChooseTag tag = new ChooseTag( chooseTI );

        MarmaladeTagInfo c1TI = new MarmaladeTagInfo(  );
        DefaultRawAttributes c1TIAttrs = new DefaultRawAttributes(  );

        c1TIAttrs.addAttribute( new DefaultRawAttribute( "", "test", "false" ) );
        c1TI.setAttributes( c1TIAttrs );
        c1TI.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        WhenTag c1 = new WhenTag( c1TI );

        MarmaladeTagInfo c1fTI = new MarmaladeTagInfo(  );

        c1fTI.setAttributes( new DefaultRawAttributes(  ) );

        FlagChildTestTag c1f = new FlagChildTestTag( c1fTI );

        c1.addChild( c1f );
        c1f.setParent( c1 );
        tag.addChild( c1 );
        c1.setParent( tag );

        MarmaladeTagInfo c2TI = new MarmaladeTagInfo(  );
        DefaultRawAttributes c2TIAttrs = new DefaultRawAttributes(  );

        c2TIAttrs.addAttribute( new DefaultRawAttribute( "", "test", "true" ) );
        c2TI.setAttributes( c2TIAttrs );
        c2TI.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        WhenTag c2 = new WhenTag( c2TI );

        MarmaladeTagInfo c2fTI = new MarmaladeTagInfo(  );

        c2fTI.setAttributes( new DefaultRawAttributes(  ) );

        FlagChildTestTag c2f = new FlagChildTestTag( c2fTI );

        c2.addChild( c2f );
        c2f.setParent( c2 );
        tag.addChild( c2 );
        c2.setParent( tag );

        MarmaladeTagInfo oTI = new MarmaladeTagInfo(  );

        oTI.setAttributes( new DefaultRawAttributes(  ) );

        OtherwiseTag o = new OtherwiseTag( oTI );

        MarmaladeTagInfo ofTI = new MarmaladeTagInfo(  );

        ofTI.setAttributes( new DefaultRawAttributes(  ) );

        FlagChildTestTag of = new FlagChildTestTag( ofTI );

        o.addChild( of );
        of.setParent( o );
        tag.addChild( o );
        o.setParent( tag );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertFalse( "Condition 1 should NOT have fired.", c1f.fired(  ) );
        assertTrue( "Condition 2 should have fired.", c2f.fired(  ) );
        assertFalse( "Otherwise should NOT have fired.", of.fired(  ) );
    }

    public void testShouldExecuteOtherwiseIfNoWhenChildTestResultIsTrue(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        MarmaladeTagInfo chooseTI = new MarmaladeTagInfo(  );
        DefaultRawAttributes chooseTIAttrs = new DefaultRawAttributes(  );

        chooseTI.setAttributes( chooseTIAttrs );

        ChooseTag tag = new ChooseTag( chooseTI );

        MarmaladeTagInfo c1TI = new MarmaladeTagInfo(  );
        DefaultRawAttributes c1TIAttrs = new DefaultRawAttributes(  );

        c1TIAttrs.addAttribute( new DefaultRawAttribute( "", "test", "false" ) );
        c1TI.setAttributes( c1TIAttrs );
        c1TI.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        WhenTag c1 = new WhenTag( c1TI );

        MarmaladeTagInfo c1fTI = new MarmaladeTagInfo(  );

        c1fTI.setAttributes( new DefaultRawAttributes(  ) );

        FlagChildTestTag c1f = new FlagChildTestTag( c1fTI );

        c1.addChild( c1f );
        c1f.setParent( c1 );
        tag.addChild( c1 );
        c1.setParent( tag );

        MarmaladeTagInfo c2TI = new MarmaladeTagInfo(  );
        DefaultRawAttributes c2TIAttrs = new DefaultRawAttributes(  );

        c2TIAttrs.addAttribute( new DefaultRawAttribute( "", "test", "false" ) );
        c2TI.setAttributes( c2TIAttrs );
        c2TI.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        WhenTag c2 = new WhenTag( c2TI );

        MarmaladeTagInfo c2fTI = new MarmaladeTagInfo(  );

        c2fTI.setAttributes( new DefaultRawAttributes(  ) );

        FlagChildTestTag c2f = new FlagChildTestTag( c2fTI );

        c2.addChild( c2f );
        c2f.setParent( c2 );
        tag.addChild( c2 );
        c2.setParent( tag );

        MarmaladeTagInfo oTI = new MarmaladeTagInfo(  );

        oTI.setAttributes( new DefaultRawAttributes(  ) );

        OtherwiseTag o = new OtherwiseTag( oTI );

        MarmaladeTagInfo ofTI = new MarmaladeTagInfo(  );

        ofTI.setAttributes( new DefaultRawAttributes(  ) );

        FlagChildTestTag of = new FlagChildTestTag( ofTI );

        o.addChild( of );
        of.setParent( o );
        tag.addChild( o );
        o.setParent( tag );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertFalse( "Condition 1 should NOT have fired.", c1f.fired(  ) );
        assertFalse( "Condition 2 should NOT have fired.", c2f.fired(  ) );
        assertTrue( "Otherwise should have fired.", of.fired(  ) );
    }

    public void testShouldFirstWhenChildWithTrueTestResultAndNoOthers_FF(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        MarmaladeTagInfo chooseTI = new MarmaladeTagInfo(  );
        DefaultRawAttributes chooseTIAttrs = new DefaultRawAttributes(  );

        chooseTI.setAttributes( chooseTIAttrs );

        ChooseTag tag = new ChooseTag( chooseTI );

        MarmaladeTagInfo c1TI = new MarmaladeTagInfo(  );
        DefaultRawAttributes c1TIAttrs = new DefaultRawAttributes(  );

        c1TIAttrs.addAttribute( new DefaultRawAttribute( "", "test", "false" ) );
        c1TI.setAttributes( c1TIAttrs );
        c1TI.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        WhenTag c1 = new WhenTag( c1TI );

        MarmaladeTagInfo c1fTI = new MarmaladeTagInfo(  );

        c1fTI.setAttributes( new DefaultRawAttributes(  ) );

        FlagChildTestTag c1f = new FlagChildTestTag( c1fTI );

        c1.addChild( c1f );
        c1f.setParent( c1 );
        tag.addChild( c1 );
        c1.setParent( tag );

        MarmaladeTagInfo c2TI = new MarmaladeTagInfo(  );
        DefaultRawAttributes c2TIAttrs = new DefaultRawAttributes(  );

        c2TIAttrs.addAttribute( new DefaultRawAttribute( "", "test", "false" ) );
        c2TI.setAttributes( c2TIAttrs );
        c2TI.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        WhenTag c2 = new WhenTag( c2TI );

        MarmaladeTagInfo c2fTI = new MarmaladeTagInfo(  );

        c2fTI.setAttributes( new DefaultRawAttributes(  ) );

        FlagChildTestTag c2f = new FlagChildTestTag( c2fTI );

        c2.addChild( c2f );
        c2f.setParent( c2 );
        tag.addChild( c2 );
        c2.setParent( tag );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertFalse( "Condition 1 should NOT have fired.", c1f.fired(  ) );
        assertFalse( "Condition 2 should NOT have fired.", c2f.fired(  ) );
    }

    public void testShouldExecuteOtherwiseWhenNoWhenChildPresent(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        MarmaladeTagInfo chooseTI = new MarmaladeTagInfo(  );
        DefaultRawAttributes chooseTIAttrs = new DefaultRawAttributes(  );

        chooseTI.setAttributes( chooseTIAttrs );

        ChooseTag tag = new ChooseTag( chooseTI );

        MarmaladeTagInfo oTI = new MarmaladeTagInfo(  );

        oTI.setAttributes( new DefaultRawAttributes(  ) );

        OtherwiseTag o = new OtherwiseTag( oTI );

        MarmaladeTagInfo ofTI = new MarmaladeTagInfo(  );

        ofTI.setAttributes( new DefaultRawAttributes(  ) );

        FlagChildTestTag of = new FlagChildTestTag( ofTI );

        o.addChild( of );
        of.setParent( o );
        tag.addChild( o );
        o.setParent( tag );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertTrue( "Otherwise should have fired.", of.fired(  ) );
    }
}
