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



* Created on Apr 22, 2004 */
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