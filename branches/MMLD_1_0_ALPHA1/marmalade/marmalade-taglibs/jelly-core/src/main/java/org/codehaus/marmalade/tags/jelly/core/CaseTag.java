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
/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.AbstractConditionalTag;
import org.codehaus.marmalade.tags.jelly.AbstractJellyConditionalTag;

/**
 * @author jdcasey
 */
public class CaseTag extends AbstractJellyConditionalTag
{
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String FALL_THROUGH_ATTRIBUTE = "fallThru";
    private Object testTarget;
    private boolean fallThrough = false;

    public CaseTag(  )
    {
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        requireParent( SwitchTag.class );

        if ( conditionMatches( context ) )
        {
            processChildren( context );

            Boolean fallThrough = ( Boolean ) getAttributes(  ).getValue( FALL_THROUGH_ATTRIBUTE,
                    Boolean.class, context, Boolean.FALSE );

            this.fallThrough = fallThrough.booleanValue(  );
        }
    }

    public void setTestTarget( Object testObject )
    {
        this.testTarget = testObject;
    }

    public boolean fallThrough(  )
    {
        return fallThrough;
    }

    public boolean conditionMatches( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        return requireTagAttribute( VALUE_ATTRIBUTE, context ).equals( testTarget );
    }

    protected void doReset(  )
    {
        testTarget = null;
        fallThrough = false;
        super.doReset(  );
    }
}
