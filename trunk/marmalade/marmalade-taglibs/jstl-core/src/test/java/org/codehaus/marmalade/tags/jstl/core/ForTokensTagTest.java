
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

/* Created on Apr 15, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.PassThroughExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;

/**
 * @author jdcasey
 */
public class ForTokensTagTest extends TestCase
{
    private void _testTokenIteration( String delim, String value,
        int expectedResult ) throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "items", value );
        attrs.addAttribute( "", "delims", delim );
        attrs.addAttribute( "", "var", "item" );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new PassThroughExpressionEvaluator(  ) );

        ForTokensTag tag = new ForTokensTag( ti );

        MarmaladeTagInfo counterTI = new MarmaladeTagInfo(  );

        counterTI.setAttributes( new DefaultRawAttributes(  ) );

        CounterTestTag counter = new CounterTestTag( counterTI );

        counter.setParent( tag );
        tag.addChild( counter );

        tag.execute( new DefaultContext(  ) );

        assertEquals( expectedResult, counter.counter(  ) );
    }

    public void testDoExecute_SingleDelim_SingleValue(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        _testTokenIteration( ",", "one", 1 );
    }

    public void testDoExecute_SingleDelim_MultiValue(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        _testTokenIteration( ",", "one,two", 2 );
    }

    public void testDoExecute_MultiDelim_SingleValue(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        _testTokenIteration( ",;", "one", 1 );
    }

    public void testDoExecute_MultiDelim_MultiValue(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        _testTokenIteration( ",;", "one,two;three", 3 );
    }
}
