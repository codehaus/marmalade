
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

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.structure.AbstractWebResponseSubTag;

/**
 * @author jdcasey
 */
public abstract class AbstractAssertionTag extends AbstractWebResponseSubTag
{
    protected AbstractAssertionTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected abstract boolean test( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException;

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        setUp( context );
        processChildren( context );

        if ( !test( context ) )
        {
            throw buildException(  );
        }
    }

    protected void setUp( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
    }

    protected void tearDown(  )
    {
    }

    protected HttpAssertionFailedException buildException(  )
    {
        return new HttpAssertionFailedException( "Assertion failed." );
    }

    protected void doReset(  )
    {
        tearDown(  );
        super.doReset(  );
    }
}
