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
/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.core;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.TagExecutionException;

/**
 * @author jdcasey
 */
public class CatchTag
    extends AbstractMarmaladeTag
{
    public static final String VAR_ATTRIBUTE = "var";

    public static final String CLASS_ATTRIBUTE = "class";

    public CatchTag()
    {
    }

    protected void doExecute( MarmaladeExecutionContext context ) throws MarmaladeExecutionException
    {
        Object binding = (String) requireTagAttribute( VAR_ATTRIBUTE, context );

        try
        {
            processChildren( context );
        }
        catch ( Throwable e )
        {
            String className = (String) getAttributes().getValue( CLASS_ATTRIBUTE, String.class, context );

            if ( (className != null) && (className.length() > 0) )
            {
                if ( e.getClass().getName().equals( className ) )
                {
                    context.setVariable( binding, e );
                }
                else
                {
                    throw new TagExecutionException( getTagInfo(), "Exception thrown inside catch tag was not specified to be caught.", e );
                }
            }
            else
            {
                context.setVariable( binding, e );
            }
        }
    }
}