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



* Created on Apr 23, 2004 */
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