/* Created on Aug 10, 2004 */
package org.codehaus.marmalade.instrumentation.testExceptionPkg;

import org.codehaus.marmalade.metamodel.AbstractMarmaladeTagLibrary;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class TestFailureTaglib
    extends AbstractMarmaladeTagLibrary
{
    public TestFailureTaglib()
    {
        registerTag( "testFailure", TestFailureTag.class );
    }

    public static final class TestFailureTag
        extends AbstractMarmaladeTag
    {
        protected void doExecute( MarmaladeExecutionContext context ) throws MarmaladeExecutionException
        {
            TestRBException ex = new TestRBException( "test" );

            throw ex;
        }
    }
}