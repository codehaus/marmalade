
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

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jstl.core.SetTag;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;

import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class SetTagTest extends TestCase
{
    public void testDoExecute_VarValue(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes();

        attrs.addAttribute( "", SetTag.VAR_ATTRIBUTE, "testKey" );
        attrs.addAttribute( "", SetTag.VALUE_ATTRIBUTE, "value" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attrs);

        SetTag tag = new SetTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        assertNull( "Variable \'testKey\' should not be in context.",
            ctx.getVariable( "testKey", null ) );
        tag.execute( ctx );
        assertEquals( "Can't verify successful setVariable() operation.",
            "value", ctx.getVariable( "testKey", null ) );
    }

    public void testDoExecute_PropertyTargetValue(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes();

        attrs.addAttribute( "", SetTag.PROPERTY_ATTRIBUTE, "attribute.value" );
        attrs.addAttribute( "", SetTag.TARGET_ATTRIBUTE, "#subject" );
        attrs.addAttribute( "", SetTag.VALUE_ATTRIBUTE, "testResult" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attrs);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());

        SetTag tag = new SetTag( mti );

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
