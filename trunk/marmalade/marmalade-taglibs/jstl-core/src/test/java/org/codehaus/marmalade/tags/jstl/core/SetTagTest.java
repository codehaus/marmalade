
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
public class SetTagTest extends TestCase
{
    public void testShouldRequireVarAttribute(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "value", "test" ) );
        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        SetTag tag = new SetTag( ti );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "should fail because of missing var attribute" );
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing var attribute.
        }
    }

    public void testShouldRequireValueAttributeWithMissingBody(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "var", "test" ) );
        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        SetTag tag = new SetTag( ti );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "should fail because of missing value attribute and body" );
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing var attribute and body.
        }
    }

    public void testShouldNotRequireValueAttributeWithSpecifiedBody(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "var", "test" ) );
        ti.setAttributes( attrs );
        ti.appendText( "value".toCharArray(  ), 0, "value".length(  ) );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        SetTag tag = new SetTag( ti );

        tag.execute( new DefaultContext(  ) );
    }

    public void testShouldSetContextVariableWithVarAndValueAttributes(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "var", "testKey" ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "value", "value" ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        SetTag tag = new SetTag( ti );

        DefaultContext ctx = new DefaultContext(  );

        assertNull( "Variable \'testKey\' should not be in context.",
            ctx.getVariable( "testKey", null ) );
        tag.execute( ctx );
        assertEquals( "Can't verify successful setVariable() operation.",
            "value", ctx.getVariable( "testKey", null ) );
    }

    public void testShouldSetTargetPropertyWithTargetPropertyAndValueAttributes(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "property",
                "attribute.value" ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "target", "#subject" ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "value", "testResult" ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        SetTag tag = new SetTag( ti );

        DefaultContext ctx = new DefaultContext(  );

        TestSubject subject = new TestSubject( new TestAttribute( "testSource" ) );

        ctx.setVariable( "subject", subject );

        assertEquals( "Pre-Test: subject's attribute's value should be \'testSource\'",
            "testSource", subject.getAttribute(  ).getValue(  ) );
        tag.execute( ctx );
        assertEquals( "Post-Test: subject's attribute's value should be \'testResult\'",
            "testResult", subject.getAttribute(  ).getValue(  ) );
    }
}
