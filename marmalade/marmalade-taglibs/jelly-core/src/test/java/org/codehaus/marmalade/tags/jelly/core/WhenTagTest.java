
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
public class WhenTagTest extends TestCase
{
    public void testDoExecute_NoParent(  )
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", WhenTag.TEST_ATTRIBUTE, "true" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        WhenTag tag = new WhenTag( mti );

        FlagChildTestTag flag = new FlagChildTestTag( new MarmaladeTagInfo(  ) );

        flag.setParent( tag );
        tag.addChild( flag );

        DefaultContext context = new DefaultContext(  );

        try
        {
            tag.execute( context );
            fail( "Tag should fail due to missing ChooseTag parent." );
        }
        catch ( MarmaladeExecutionException e )
        {
        }
    }

    public void testDoExecute_WithParent(  )
        throws MarmaladeExecutionException
    {
        ChooseTag parent = new ChooseTag( new MarmaladeTagInfo() );

        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", WhenTag.TEST_ATTRIBUTE, "true");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        WhenTag tag = new WhenTag( mti );

        parent.addChild( tag );
        tag.setParent( parent );

        FlagChildTestTag flag = new FlagChildTestTag( new MarmaladeTagInfo() );

        tag.addChild( flag );
        flag.setParent( tag );

        DefaultContext context = new DefaultContext(  );

        tag.execute( context );
        assertTrue( "Child tag should have fired.", flag.fired(  ) );
    }
}
