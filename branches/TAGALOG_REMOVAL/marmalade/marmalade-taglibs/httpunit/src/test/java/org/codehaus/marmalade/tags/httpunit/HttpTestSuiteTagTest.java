/* 
 * 
 * Copyright (c) 2004 John Dennis Casey
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to 
 * deal in the Software without restriction, including without limitation the 
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
 * sell copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 * 
 */



* Created on Apr 25, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestResult;

import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.MockObjectTestCase;

import java.util.Enumeration;

/**
 * @author jdcasey
 */
public class HttpTestSuiteTagTest extends MockObjectTestCase
{
    public void testSuccess(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DummyTC tc = new DummyTC( false, false );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "var", "testResult" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "httpTestSuite" );

        HttpTestSuiteTag tag = new HttpTestSuiteTag( mti );

        tag.addTest( tc );

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
        DummyTC tc = new DummyTC( false, true );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "var", "testResult" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "httpTestSuite" );

        HttpTestSuiteTag tag = new HttpTestSuiteTag( mti );

        tag.addTest( tc );

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
        DummyTC tc = new DummyTC( true, false );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "var", "testResult" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "httpTestSuite" );

        HttpTestSuiteTag tag = new HttpTestSuiteTag( mti );

        tag.addTest( tc );

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

    public static final class DummyTC extends TestCase
    {
        private boolean error = false;
        private boolean fail = false;

        DummyTC( boolean error, boolean fail )
        {
            super( "testMe" );
            this.error = error;
            this.fail = fail;
        }

        public void testMe(  )
        {
            if ( error )
            {
                throw new UnsupportedOperationException( "Test Error" );
            }
            else if ( fail )
            {
                throw new AssertionFailedError( "Test Failure" );
            }
            else
            {
                return;
            }
        }
    }
}