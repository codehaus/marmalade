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



* Created on May 8, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.MockObjectTestCase;

/**
 * @author jdcasey
 */
public class AbstractAssertionTagTest extends MockObjectTestCase
{
    public void testShouldSucceedOnPositiveTestResult(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        TestLinkSubTag tlst = new TestLinkSubTag( true );

        DefaultContext ctx = new DefaultContext(  );

        tlst.execute( ctx );
    }

    public void testShouldFailWithHttpAssertionFailedExceptionOnNegativeTestResult(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        TestLinkSubTag tlst = new TestLinkSubTag( false );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tlst.execute( ctx );
            fail( "Should have failed with HttpAssertionFailedException." );
        }
        catch ( HttpAssertionFailedException e )
        {
        }
    }

    private static final class TestLinkSubTag extends AbstractAssertionTag
    {
        private boolean shouldSucceed;

        TestLinkSubTag( boolean shouldSucceed )
        {
            super( new MarmaladeTagInfo() );
            this.shouldSucceed = shouldSucceed;
        }

        protected boolean test( MarmaladeExecutionContext context )
            throws MarmaladeExecutionException
        {
            return shouldSucceed;
        }
    }
}