
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

/* Created on Apr 23, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import junit.framework.TestResult;

import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.MockObjectTestCase;
import org.jmock.cglib.Mock;

import java.util.Enumeration;

/**
 * @author jdcasey
 */
public class HttpTestTagTest extends MockObjectTestCase
{
    public void testSuccess(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        Mock childMock = new Mock( MarmaladeTag.class );

        childMock.expects( once(  ) ).method( "execute" )
                 .with( isA( MarmaladeExecutionContext.class ) ).isVoid(  );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "name", "UnitTest" ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "var", "testResult" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "httpTest" );

        HttpTestTag tag = new HttpTestTag( mti );

        tag.addChild( ( MarmaladeTag ) childMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        TestResult result = ( TestResult ) ctx.getVariable( "testResult", null );

        assertNotNull( "Context should contain test result.", result );

        Enumeration errors = result.errors(  );

        for ( ; errors.hasMoreElements(  ); )
        {
            System.out.println( "Error: " + errors.nextElement(  ) );
        }

        Enumeration failures = result.failures(  );

        for ( ; failures.hasMoreElements(  ); )
        {
            System.out.println( "Failure: " + failures.nextElement(  ) );
        }

        assertEquals( "Should have 0 errors", 0, result.errorCount(  ) );
        assertEquals( "Should have 0 failures", 0, result.failureCount(  ) );
        assertEquals( "Should have 1 run", 1, result.runCount(  ) );
        assertTrue( "Test Result should have passed", result.wasSuccessful(  ) );
    }

    public void testFailure(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        Mock childMock = new Mock( MarmaladeTag.class );

        childMock.expects( once(  ) ).method( "execute" )
                 .with( isA( MarmaladeExecutionContext.class ) ).will( throwException( 
                new HttpAssertionFailedException(  ) ) );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "name", "UnitTest" ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "var", "testResult" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "httpTest" );

        HttpTestTag tag = new HttpTestTag( mti );

        tag.addChild( ( MarmaladeTag ) childMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        TestResult result = ( TestResult ) ctx.getVariable( "testResult", null );

        assertNotNull( "Context should contain test result.", result );

        Enumeration errors = result.errors(  );

        for ( ; errors.hasMoreElements(  ); )
        {
            System.out.println( "Error: " + errors.nextElement(  ) );
        }

        Enumeration failures = result.failures(  );

        for ( ; failures.hasMoreElements(  ); )
        {
            System.out.println( "Failure: " + failures.nextElement(  ) );
        }

        assertEquals( "Should have 0 errors", 0, result.errorCount(  ) );
        assertEquals( "Should have 1 failures", 1, result.failureCount(  ) );
        assertEquals( "Should have 1 run", 1, result.runCount(  ) );
        assertFalse( "Test Result should NOT have passed",
            result.wasSuccessful(  ) );
    }

    public void testError(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        Mock childMock = new Mock( MarmaladeTag.class );

        childMock.expects( once(  ) ).method( "execute" )
                 .with( isA( MarmaladeExecutionContext.class ) ).will( throwException( 
                new NullPointerException(  ) ) );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "name", "UnitTest" ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "var", "testResult" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "httpTest" );

        HttpTestTag tag = new HttpTestTag( mti );

        tag.addChild( ( MarmaladeTag ) childMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        TestResult result = ( TestResult ) ctx.getVariable( "testResult", null );

        assertNotNull( "Context should contain test result.", result );

        Enumeration errors = result.errors(  );

        for ( ; errors.hasMoreElements(  ); )
        {
            System.out.println( "Error: " + errors.nextElement(  ) );
        }

        Enumeration failures = result.failures(  );

        for ( ; failures.hasMoreElements(  ); )
        {
            System.out.println( "Failure: " + failures.nextElement(  ) );
        }

        assertEquals( "Should have 1 errors", 1, result.errorCount(  ) );
        assertEquals( "Should have 0 failures", 0, result.failureCount(  ) );
        assertEquals( "Should have 1 run", 1, result.runCount(  ) );
        assertFalse( "Test Result should NOT have passed",
            result.wasSuccessful(  ) );
    }
}
