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
/* Created on Jun 23, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

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
public class GetStaticTagTest extends TestCase
{
    public static final String TEST_FIELD = "test";

    public void testShouldRequireClassNameAttribute(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", GetStaticTag.FIELD_ATTRIBUTE,
            "FIELD_NAME" );
        attributes.addAttribute( "", "", GetStaticTag.VAR_ATTRIBUTE, "var" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        GetStaticTag tag = new GetStaticTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "Should fail with missing className attribute." );
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing className attribute.
        }
    }

    public void testShouldRequireFieldAttribute(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", GetStaticTag.CLASS_NAME_ATTRIBUTE,
            getClass(  ).getName(  ) );
        attributes.addAttribute( "", "", GetStaticTag.VAR_ATTRIBUTE, "var" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        GetStaticTag tag = new GetStaticTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "Should fail with missing field attribute." );
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing field attribute.
        }
    }

    public void testShouldRequireVarAttribute(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", GetStaticTag.FIELD_ATTRIBUTE,
            "FIELD_NAME" );
        attributes.addAttribute( "", "", GetStaticTag.CLASS_NAME_ATTRIBUTE,
            getClass(  ).getName(  ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        GetStaticTag tag = new GetStaticTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "Should fail with missing var attribute." );
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing var attribute.
        }
    }

    public void testShouldFailWithBadClassName(  )
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", GetStaticTag.CLASS_NAME_ATTRIBUTE,
            "org.somewhere.BadClass" );
        attributes.addAttribute( "", "", GetStaticTag.FIELD_ATTRIBUTE,
            "FIELD_NAME" );
        attributes.addAttribute( "", "", GetStaticTag.VAR_ATTRIBUTE, "var" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        GetStaticTag tag = new GetStaticTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "Should fail with invalid className specification." );
        }
        catch ( MarmaladeExecutionException e )
        {
            // should snag on invalid className spec.
        }
    }

    public void testShouldFailWithBadField(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", GetStaticTag.CLASS_NAME_ATTRIBUTE,
            getClass(  ).getName(  ) );
        attributes.addAttribute( "", "", GetStaticTag.FIELD_ATTRIBUTE,
            "FIELD_NAME" );
        attributes.addAttribute( "", "", GetStaticTag.VAR_ATTRIBUTE, "var" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        GetStaticTag tag = new GetStaticTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "Should fail with invalid field specification." );
        }
        catch ( MarmaladeExecutionException e )
        {
            // should snag on invalid field spec.
        }
    }

    public void testShouldSucceedWithValidClassNameFieldAndVar(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", GetStaticTag.CLASS_NAME_ATTRIBUTE,
            getClass(  ).getName(  ) );
        attributes.addAttribute( "", "", GetStaticTag.FIELD_ATTRIBUTE,
            "TEST_FIELD" );
        attributes.addAttribute( "", "", GetStaticTag.VAR_ATTRIBUTE, "var" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        GetStaticTag tag = new GetStaticTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        DefaultContext context = new DefaultContext(  );

        tag.execute( context );

        assertEquals( TEST_FIELD, context.getVariable( "var", null ) );
    }
}
