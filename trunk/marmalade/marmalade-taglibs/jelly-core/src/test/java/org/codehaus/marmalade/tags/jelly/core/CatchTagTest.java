
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
public class CatchTagTest extends TestCase
{
    public void testDoExecuteWithException_NoClass(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", CatchTag.VAR_ATTRIBUTE, "exception" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );

        CatchTag tag = new CatchTag( mti );

        ErrorGeneratingTestTag errTag = new ErrorGeneratingTestTag( new MarmaladeTagInfo(  ) );

        errTag.setParent( tag );
        tag.addChild( errTag );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
        }
        catch ( Throwable e )
        {
            e.printStackTrace(  );
            fail( "Exception should have been caught in tag." );
        }

        Throwable t = ( Throwable ) ctx.getVariable( "exception", null );

        assertNotNull( "Should have caught an exception", t );
        assertTrue( "Exception should be UnsupportedOperationException",
            t instanceof UnsupportedOperationException );
    }

    public void testDoExecuteWithException_WithClass_Match(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", CatchTag.VAR_ATTRIBUTE, "exception" );
        attrs.addAttribute( "", CatchTag.CLASS_ATTRIBUTE,
            "java.lang.UnsupportedOperationException" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        CatchTag tag = new CatchTag( mti );

        ErrorGeneratingTestTag errTag = new ErrorGeneratingTestTag( new MarmaladeTagInfo(  ) );

        errTag.setParent( tag );
        tag.addChild( errTag );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
        }
        catch ( Throwable e )
        {
            e.printStackTrace(  );
            fail( "Exception should have been caught in tag." );
        }

        Throwable t = ( Throwable ) ctx.getVariable( "exception", null );

        assertNotNull( "Should have caught an exception", t );
        assertTrue( "Exception should be UnsupportedOperationException",
            t instanceof UnsupportedOperationException );
    }

    public void testDoExecuteWithException_WithClass_NoMatch(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", CatchTag.VAR_ATTRIBUTE, "exception" );
        attrs.addAttribute( "", CatchTag.CLASS_ATTRIBUTE,
            "java.lang.IllegalArgumentException" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        CatchTag tag = new CatchTag( mti );

        ErrorGeneratingTestTag errTag = new ErrorGeneratingTestTag( new MarmaladeTagInfo(  ) );

        errTag.setParent( tag );
        tag.addChild( errTag );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( "Exception should NOT have been caught in tag." );
        }
        catch ( Throwable e )
        {
        }

        Throwable t = ( Throwable ) ctx.getVariable( "exception", null );

        assertNull( "Should have caught an exception", t );
    }
}
