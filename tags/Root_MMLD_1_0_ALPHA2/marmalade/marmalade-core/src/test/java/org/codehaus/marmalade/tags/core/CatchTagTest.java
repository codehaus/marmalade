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
package org.codehaus.marmalade.tags.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;

/**
 * @author jdcasey
 */
public class CatchTagTest
    extends TestCase
{
    public void testShouldRequireVarAttribute() throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo();

        CatchTag tag = new CatchTag();

        tag.setTagInfo( ti );
        tag.setAttributes( new DefaultAttributes() );

        try
        {
            tag.execute( new DefaultContext() );
            fail( "var attribute should be required" );
        }
        catch ( MissingAttributeException e )
        {
            // should catch this exception; "var" attribute should be required.
        }
    }

    public void testShouldCatchThrownExceptionWhenClassIsUnspecified() throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo();
        DefaultRawAttributes rawAttrs = new DefaultRawAttributes();

        rawAttrs.addAttribute( "", "", "var", "exception" );

        DefaultAttributes attrs = new DefaultAttributes( rawAttrs );

        CatchTag tag = new CatchTag();

        tag.setTagInfo( ti );
        tag.setAttributes( attrs );

        MarmaladeTagInfo cti = new MarmaladeTagInfo();

        ErrorGeneratingTestTag errTag = new ErrorGeneratingTestTag();

        errTag.setTagInfo( cti );

        tag.addChild( errTag );
        errTag.setParent( tag );

        DefaultContext ctx = new DefaultContext();

        try
        {
            tag.execute( ctx );
        }
        catch ( Throwable e )
        {
            e.printStackTrace();
            fail( "Exception should have been caught in tag." );
        }

        Throwable t = (Throwable) ctx.getVariable( "exception", null );

        assertNotNull( "Should have caught an exception", t );
        assertTrue( "Exception should be UnsupportedOperationException", t instanceof UnsupportedOperationException );
    }

    public void testShouldCatchThrownAndMatchedExceptionClassWhenSpecified() throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo();
        DefaultRawAttributes rawAttrs = new DefaultRawAttributes();

        rawAttrs.addAttribute( "", "", "var", "exception" );
        rawAttrs.addAttribute( "", "", "class", "java.lang.UnsupportedOperationException" );

        DefaultAttributes attrs = new DefaultAttributes( rawAttrs );

        CatchTag tag = new CatchTag();

        tag.setTagInfo( ti );
        tag.setAttributes( attrs );

        MarmaladeTagInfo cti = new MarmaladeTagInfo();

        ErrorGeneratingTestTag errTag = new ErrorGeneratingTestTag();

        errTag.setTagInfo( cti );

        tag.addChild( errTag );
        errTag.setParent( tag );

        DefaultContext ctx = new DefaultContext();

        try
        {
            tag.execute( ctx );
        }
        catch ( Throwable e )
        {
            e.printStackTrace();
            fail( "Exception should have been caught in tag." );
        }

        Throwable t = (Throwable) ctx.getVariable( "exception", null );

        assertNotNull( "Should have caught an exception", t );
        assertTrue( "Exception should be UnsupportedOperationException", t instanceof UnsupportedOperationException );
    }

    public void testShouldFailToCatchThrownAndUnmatchedExceptionClassWhenSpecified() throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo();
        DefaultRawAttributes rawAttrs = new DefaultRawAttributes();

        rawAttrs.addAttribute( "", "", "var", "exception" );
        rawAttrs.addAttribute( "", "", "class", "java.lang.IllegalArgumentException" );

        DefaultAttributes attrs = new DefaultAttributes( rawAttrs );

        CatchTag tag = new CatchTag();

        tag.setTagInfo( ti );
        tag.setAttributes( attrs );

        MarmaladeTagInfo cti = new MarmaladeTagInfo();

        ErrorGeneratingTestTag errTag = new ErrorGeneratingTestTag();

        errTag.setTagInfo( cti );

        tag.addChild( errTag );
        errTag.setParent( tag );

        DefaultContext ctx = new DefaultContext();

        try
        {
            tag.execute( ctx );
            fail( "Exception should NOT have been caught in tag." );
        }
        catch ( Throwable e )
        {
        }

        Throwable t = (Throwable) ctx.getVariable( "exception", null );

        assertNull( "Should have caught an exception", t );
    }
}