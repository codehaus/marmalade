/* Created on Aug 10, 2004 */
package org.codehaus.marmalade.instrumentation;

import org.codehaus.marmalade.metamodel.AbstractMarmaladeTagLibrary;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.TagExecutionException;

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
            MarmaladeExecutionException ex = new TagExecutionException( getTagInfo(), "test exception" );

            throw ex;
        }
    }
}