
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

/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.IllegalParentException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;

/**
 * @author jdcasey
 */
public class WhenTagTest extends TestCase
{
    public void testShouldRequireChooseParent(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "test", "true" ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        WhenTag tag = new WhenTag( ti );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "should fail because of missing choose parent" );
        }
        catch ( IllegalParentException e )
        {
            // should snag on the missing choose parent
        }
    }

    public void testShouldRequireTestAttribute(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( new DefaultRawAttributes(  ) );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        WhenTag tag = new WhenTag( ti );

        MarmaladeTagInfo parentTI = new MarmaladeTagInfo(  );

        parentTI.setAttributes( new DefaultRawAttributes(  ) );

        ChooseTag parent = new ChooseTag( parentTI );

        parent.addChild( tag );
        tag.setParent( parent );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "should fail because of missing test attribute" );
        }
        catch ( MissingAttributeException e )
        {
            // should snag on the missing test attribute
        }
    }

    public void testShouldExecuteChildrenWhenTestResultIsTrue(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "test", "true" ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        WhenTag tag = new WhenTag( ti );

        MarmaladeTagInfo parentTI = new MarmaladeTagInfo(  );

        parentTI.setAttributes( new DefaultRawAttributes(  ) );

        ChooseTag parent = new ChooseTag( parentTI );

        parent.addChild( tag );
        tag.setParent( parent );

        MarmaladeTagInfo flagTI = new MarmaladeTagInfo(  );

        FlagChildTestTag flag = new FlagChildTestTag( flagTI );

        tag.addChild( flag );
        flag.setParent( tag );

        DefaultContext context = new DefaultContext(  );

        tag.execute( context );
        assertTrue( "Child tag should have fired.", flag.fired(  ) );
    }

    public void testShouldNotExecuteChildrenWhenTestResultIsFalse(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "test", "false" ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        WhenTag tag = new WhenTag( ti );

        MarmaladeTagInfo parentTI = new MarmaladeTagInfo(  );

        parentTI.setAttributes( new DefaultRawAttributes(  ) );

        ChooseTag parent = new ChooseTag( parentTI );

        parent.addChild( tag );
        tag.setParent( parent );

        MarmaladeTagInfo flagTI = new MarmaladeTagInfo(  );

        FlagChildTestTag flag = new FlagChildTestTag( flagTI );

        tag.addChild( flag );
        flag.setParent( tag );

        DefaultContext context = new DefaultContext(  );

        tag.execute( context );
        assertFalse( "Child tag should NOT have fired.", flag.fired(  ) );
    }
}
