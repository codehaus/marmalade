
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

/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestResult;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class HttpTestTag extends AbstractMarmaladeTag
{
    public static final String NAME_ATTRIBUTE = "name";
    public static final String VAR_ATTRIBUTE = "var";

    public HttpTestTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        String name = ( String ) requireTagAttribute( NAME_ATTRIBUTE,
                String.class, context );
        ScriptedTest test = new ScriptedTest( name, context );

        MarmaladeTag parent = getParent(  );

        if ( ( parent != null ) && ( parent instanceof HttpTestSuiteTag ) )
        {
            ( ( HttpTestSuiteTag ) parent ).addTest( test );
        }
        else
        {
            String var = ( String ) requireTagAttribute( VAR_ATTRIBUTE,
                    String.class, context );
            TestResult result = test.run(  );

            context.setVariable( var, result );
        }
    }

    public final class ScriptedTest extends TestCase
    {
        private MarmaladeExecutionContext context;
        private String name;

        ScriptedTest( String name, MarmaladeExecutionContext context )
        {
            super( "testScriptedAssertions" );
            this.context = context;
            this.name = name;
        }

        public String getName(  )
        {
            return name;
        }

        public void testScriptedAssertions(  )
        {
            try
            {
                processChildren( context );
            }
            catch ( MarmaladeExecutionException e )
            {
                e.printStackTrace( context.getErrWriter(  ) );
                throw new AssertionFailedError( 
                    "Test of scripted elements failed. Reason: "
                    + e.getMessage(  ) );
            }
            catch ( HttpAssertionFailedException e )
            {
                e.printStackTrace( context.getErrWriter(  ) );
                throw new AssertionFailedError( 
                    "Test of scripted elements failed.\n\nReason: "
                    + e.getMessage(  ) );
            }
        }
    }
}
