
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
