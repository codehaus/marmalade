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

import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class UseListTagTest extends TestCase
{
    public void testShouldRequireVarAttribute(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", UseListTag.ITEMS_ATTRIBUTE, "${items}" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        UseListTag tag = new UseListTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "Should require var attribute." );
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing var attribute.
        }
    }

    public void testShouldRequireItemsAttribute(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", UseListTag.VAR_ATTRIBUTE, "var" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        UseListTag tag = new UseListTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "Should require items attribute." );
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing items attribute.
        }
    }

    public void testShouldCreateListWithStringItemBoundTo_varKey(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", UseListTag.ITEMS_ATTRIBUTE, "testItem" );
        attributes.addAttribute( "", "", UseListTag.VAR_ATTRIBUTE, "varKey" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        UseListTag tag = new UseListTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        DefaultContext context = new DefaultContext(  );

        tag.execute( context );

        List list = ( List ) context.getVariable( "varKey", null );

        assertNotNull( list );
        assertEquals( 1, list.size(  ) );
        assertEquals( "testItem", list.get( 0 ) );
    }

    public void testShouldCreateLinkedListWithStringItemBoundTo_varKey(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", UseListTag.ITEMS_ATTRIBUTE, "testItem" );
        attributes.addAttribute( "", "", UseListTag.VAR_ATTRIBUTE, "varKey" );
        attributes.addAttribute( "", "", UseListTag.CLASS_ATTRIBUTE,
            "java.util.LinkedList" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        UseListTag tag = new UseListTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        DefaultContext context = new DefaultContext(  );

        tag.execute( context );

        List list = ( List ) context.getVariable( "varKey", null );

        assertNotNull( list );
        assertEquals( LinkedList.class, list.getClass(  ) );
        assertEquals( 1, list.size(  ) );
        assertEquals( "testItem", list.get( 0 ) );
    }

    public void testShouldCreateListWithItemsListContentsBoundTo_varKey(  )
        throws MarmaladeExecutionException
    {
        List items = new ArrayList(  );

        items.add( "item1" );
        items.add( "item2" );
        items.add( "item3" );

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", UseListTag.ITEMS_ATTRIBUTE, "${items}" );
        attributes.addAttribute( "", "", UseListTag.VAR_ATTRIBUTE, "varKey" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        UseListTag tag = new UseListTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        DefaultContext context = new DefaultContext(  );

        context.setVariable( "items", items );

        tag.execute( context );

        List list = ( List ) context.getVariable( "varKey", null );

        assertNotNull( list );
        assertEquals( 3, list.size(  ) );
        assertEquals( "item1", list.get( 0 ) );
        assertEquals( "item2", list.get( 1 ) );
        assertEquals( "item3", list.get( 2 ) );
    }

    public void testShouldCreateListWithItemsSetContentsBoundTo_varKey(  )
        throws MarmaladeExecutionException
    {
        Set items = new HashSet(  );

        items.add( "item1" );
        items.add( "item2" );
        items.add( "item3" );

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", UseListTag.ITEMS_ATTRIBUTE, "${items}" );
        attributes.addAttribute( "", "", UseListTag.VAR_ATTRIBUTE, "varKey" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        UseListTag tag = new UseListTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        DefaultContext context = new DefaultContext(  );

        context.setVariable( "items", items );

        tag.execute( context );

        List list = ( List ) context.getVariable( "varKey", null );

        assertNotNull( list );
        assertEquals( 3, list.size(  ) );
        assertTrue( list.contains( "item1" ) );
        assertTrue( list.contains( "item2" ) );
        assertTrue( list.contains( "item3" ) );
    }
}
