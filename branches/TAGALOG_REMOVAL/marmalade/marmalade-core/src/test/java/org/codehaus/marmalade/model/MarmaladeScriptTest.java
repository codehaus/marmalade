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
/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.model;

import junit.framework.TestCase;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class MarmaladeScriptTest extends TestCase
{
    public void testShouldConstructWithAndRetrieveLocation(  )
    {
        MarmaladeScript script = new MarmaladeScript( "test.mmld",
                new TestRootTag( new MarmaladeTagInfo(  ) ) );

        assertEquals( "test.mmld", script.getLocation(  ) );
    }

    public void testShouldExecute(  )
        throws MarmaladeExecutionException
    {
        TestRootTag root = new TestRootTag( new MarmaladeTagInfo(  ) );
        MarmaladeScript script = new MarmaladeScript( "test.mmld", root );

        script.execute( null );
        assertTrue( root.executed(  ) );
    }

    public static class TestRootTag extends AbstractMarmaladeTag
    {
        private boolean executed = false;

        TestRootTag( MarmaladeTagInfo tagInfo )
        {
            super( tagInfo );
        }

        boolean executed(  )
        {
            return executed;
        }

        protected void doExecute( MarmaladeExecutionContext context )
            throws MarmaladeExecutionException
        {
            this.executed = true;
        }
    }
}
