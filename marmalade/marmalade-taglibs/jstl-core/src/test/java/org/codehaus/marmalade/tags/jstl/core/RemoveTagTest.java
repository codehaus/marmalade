
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
public class RemoveTagTest extends TestCase
{
    public void testShouldRequireVarAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( new DefaultRawAttributes(  ) );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        RemoveTag tag = new RemoveTag( ti );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "should fail because of missing var attribute" );
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing var attribute
        }
    }

    public void testShouldRemoveExistingVariableFromContext(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "var", "testKey" ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        RemoveTag tag = new RemoveTag( ti );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "testKey", "value" );

        assertEquals( "Can't verify successful setVariable() operation.",
            "value", ctx.getVariable( "testKey", null ) );
        tag.execute( ctx );
        assertNull( "Variable \'testKey\' should no longer be in context.",
            ctx.getVariable( "testKey", null ) );
    }
}
