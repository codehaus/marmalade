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
package org.codehaus.marmalade.tags;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public abstract class AbstractConditionalTag
    extends AbstractMarmaladeTag
{
    public static final String TEST_ATTRIBUTE = "test";

    /**
     */
    public AbstractConditionalTag()
    {
        super();
    }

    public boolean conditionMatches( MarmaladeExecutionContext context ) throws MarmaladeExecutionException
    {
        Object value = requireTagAttribute( TEST_ATTRIBUTE, Boolean.class, context );

        boolean result = false;

        if ( value != null )
        {
            if ( value instanceof Boolean )
            {
                result = ((Boolean) value).booleanValue();
            }
            else
            {
                String test = String.valueOf( value );

                result = Boolean.valueOf( test ).booleanValue();
            }
        }

        return result;
    }

    protected void doExecute( MarmaladeExecutionContext context ) throws MarmaladeExecutionException
    {
        if ( conditionMatches( context ) )
        {
            processChildren( context );
        }
    }

    protected boolean alwaysProcessChildren()
    {
        return false;
    }
}