
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
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;

/**
 * @author jdcasey
 */
public class CatchTagTest extends TestCase
{
    public void testShouldRequireVarAttribute(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( new DefaultRawAttributes(  ) );

        CatchTag tag = new CatchTag( ti );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "var attribute should be required" );
        }
        catch ( MissingAttributeException e )
        {
            // should catch this exception; "var" attribute should be required.
        }
    }

    public void testShouldCatchThrownExceptionWhenClassIsUnspecified(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );
        DefaultRawAttributes tiAttrs = new DefaultRawAttributes(  );

        tiAttrs.addAttribute( new DefaultRawAttribute( "", "var", "exception" ) );
        ti.setAttributes( tiAttrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        CatchTag tag = new CatchTag( ti );

        MarmaladeTagInfo cti = new MarmaladeTagInfo(  );
        DefaultRawAttributes ctiAttrs = new DefaultRawAttributes(  );

        cti.setAttributes( ctiAttrs );

        ErrorGeneratingTestTag errTag = new ErrorGeneratingTestTag( cti );

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

    public void testShouldCatchThrownAndMatchedExceptionClassWhenSpecified(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );
        DefaultRawAttributes tiAttrs = new DefaultRawAttributes(  );

        tiAttrs.addAttribute( new DefaultRawAttribute( "", "var", "exception" ) );
        tiAttrs.addAttribute( new DefaultRawAttribute( "", "class",
                "java.lang.UnsupportedOperationException" ) );
        ti.setAttributes( tiAttrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        CatchTag tag = new CatchTag( ti );

        MarmaladeTagInfo cti = new MarmaladeTagInfo(  );
        DefaultRawAttributes ctiAttrs = new DefaultRawAttributes(  );

        cti.setAttributes( ctiAttrs );

        ErrorGeneratingTestTag errTag = new ErrorGeneratingTestTag( cti );

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

    public void testShouldFailToCatchThrownAndUnmatchedExceptionClassWhenSpecified(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );
        DefaultRawAttributes tiAttrs = new DefaultRawAttributes(  );

        tiAttrs.addAttribute( new DefaultRawAttribute( "", "var", "exception" ) );
        tiAttrs.addAttribute( new DefaultRawAttribute( "", "class",
                "java.lang.IllegalArgumentException" ) );
        ti.setAttributes( tiAttrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        CatchTag tag = new CatchTag( ti );

        MarmaladeTagInfo cti = new MarmaladeTagInfo(  );
        DefaultRawAttributes ctiAttrs = new DefaultRawAttributes(  );

        cti.setAttributes( ctiAttrs );

        ErrorGeneratingTestTag errTag = new ErrorGeneratingTestTag( cti );

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
