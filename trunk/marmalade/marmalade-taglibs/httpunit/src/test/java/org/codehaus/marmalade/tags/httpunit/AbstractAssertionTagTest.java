
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

/* Created on May 8, 2004 */
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
