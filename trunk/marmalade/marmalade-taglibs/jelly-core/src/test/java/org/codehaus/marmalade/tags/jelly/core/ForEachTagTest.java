
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class ForEachTagTest extends TestCase
{
    public void testDoExecute_Simple_Array(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", new String[] { "one", "two", "three" } );

        tag.execute( ctx );
        assertEquals( "Counter should read three", 3, counter.counter(  ) );
    }

    public void testDoExecute_Simple_Collection(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );
        List items = new ArrayList(  );

        items.add( "one" );
        items.add( "two" );
        items.add( "three" );

        ctx.setVariable( "items", items );

        tag.execute( ctx );
        assertEquals( "Counter should read three", 3, counter.counter(  ) );
    }

    public void testDoExecute_Simple_String(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", "one,two,three" );

        tag.execute( ctx );
        assertEquals( "Counter should read three", 3, counter.counter(  ) );
    }

    public void testDoExecute_Simple_Single(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", new Integer( 1 ) );

        tag.execute( ctx );
        assertEquals( "Counter should read one", 1, counter.counter(  ) );
    }

    public void testDoExecute_NonZeroBegin_Array(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.BEGIN_ATTRIBUTE, "1" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", new String[] { "one", "two", "three" } );

        tag.execute( ctx );
        assertEquals( "Counter should read two", 2, counter.counter(  ) );
    }

    public void testDoExecute_NonZeroBegin_Collection(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.BEGIN_ATTRIBUTE, "1" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );
        List items = new ArrayList(  );

        items.add( "one" );
        items.add( "two" );
        items.add( "three" );

        ctx.setVariable( "items", items );

        tag.execute( ctx );
        assertEquals( "Counter should read 2", 2, counter.counter(  ) );
    }

    public void testDoExecute_NonZeroBegin_String(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.BEGIN_ATTRIBUTE, "1" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", "one,two,three" );

        tag.execute( ctx );
        assertEquals( "Counter should read two", 2, counter.counter(  ) );
    }

    public void testDoExecute_NonZeroBegin_Single(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.BEGIN_ATTRIBUTE, "1" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", new Integer( 1 ) );

        tag.execute( ctx );
        assertEquals( "Counter should read zero", 0, counter.counter(  ) );
    }

    public void testDoExecute_PositiveEnd_Array(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.END_ATTRIBUTE, "1" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", new String[] { "one", "two", "three" } );

        tag.execute( ctx );
        assertEquals( "Counter should read two", 2, counter.counter(  ) );
    }

    public void testDoExecute_PositiveEnd_Collection(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.END_ATTRIBUTE, "1" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );
        List items = new ArrayList(  );

        items.add( "one" );
        items.add( "two" );
        items.add( "three" );

        ctx.setVariable( "items", items );

        tag.execute( ctx );
        assertEquals( "Counter should read two", 2, counter.counter(  ) );
    }

    public void testDoExecute_PositiveEnd_String(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.END_ATTRIBUTE, "1" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", "one,two,three" );

        tag.execute( ctx );
        assertEquals( "Counter should read two", 2, counter.counter(  ) );
    }

    public void testDoExecute_PositiveEnd_Single(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.END_ATTRIBUTE, "1" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", new Integer( 1 ) );

        tag.execute( ctx );
        assertEquals( "Counter should read 1", 1, counter.counter(  ) );
    }

    public void testDoExecute_ZeroEnd_Array(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.END_ATTRIBUTE, "0" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", new String[] { "one", "two", "three" } );

        tag.execute( ctx );
        assertEquals( "Counter should read one", 1, counter.counter(  ) );
    }

    public void testDoExecute_ZeroEnd_Collection(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.END_ATTRIBUTE, "0" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );
        List items = new ArrayList(  );

        items.add( "one" );
        items.add( "two" );
        items.add( "three" );

        ctx.setVariable( "items", items );

        tag.execute( ctx );
        assertEquals( "Counter should read one", 1, counter.counter(  ) );
    }

    public void testDoExecute_ZeroEnd_String(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.END_ATTRIBUTE, "0" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", "one,two,three" );

        tag.execute( ctx );
        assertEquals( "Counter should read one", 1, counter.counter(  ) );
    }

    public void testDoExecute_ZeroEnd_Single(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.END_ATTRIBUTE, "0" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", new Integer( 1 ) );

        tag.execute( ctx );
        assertEquals( "Counter should read 1", 1, counter.counter(  ) );
    }

    public void testDoExecute_MultiStep_Array(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.STEP_ATTRIBUTE, "2" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", new String[] { "one", "two", "three" } );

        tag.execute( ctx );
        assertEquals( "Counter should read two", 2, counter.counter(  ) );
    }

    public void testDoExecute_MultiStep_Collection(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.STEP_ATTRIBUTE, "2" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );
        List items = new ArrayList(  );

        items.add( "one" );
        items.add( "two" );
        items.add( "three" );

        ctx.setVariable( "items", items );

        tag.execute( ctx );
        assertEquals( "Counter should read two", 2, counter.counter(  ) );
    }

    public void testDoExecute_MultiStep_String(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.STEP_ATTRIBUTE, "2" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", "one,two,three" );

        tag.execute( ctx );
        assertEquals( "Counter should read two", 2, counter.counter(  ) );
    }

    public void testDoExecute_MultiStep_Single(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ForEachTag.ITEMS_ATTRIBUTE, "#items" );
        attrs.addAttribute( "", ForEachTag.VAR_ATTRIBUTE, "item" );
        attrs.addAttribute( "", ForEachTag.STEP_ATTRIBUTE, "2" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ForEachTag tag = new ForEachTag( mti );

        CounterTestTag counter = new CounterTestTag( new MarmaladeTagInfo(  ) );

        counter.setParent( tag );
        tag.addChild( counter );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "items", new Integer( 1 ) );

        tag.execute( ctx );
        assertEquals( "Counter should read 1", 1, counter.counter(  ) );
    }
}
