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
/* Created on Mar 26, 2004 */
package org.codehaus.marmalade.el.ognl;

import ognl.Ognl;
import ognl.OgnlException;

import org.codehaus.marmalade.el.AbstractExpressionEvaluator;
import org.codehaus.marmalade.el.ExpressionEvaluationException;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author jdcasey
 */
public class OgnlExpressionEvaluator extends AbstractExpressionEvaluator
{
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile( "#[^ ]+" );

    public OgnlExpressionEvaluator(  )
    {
    }

    public Object doEval( String expression, Map context, Class expectedType )
        throws ExpressionEvaluationException
    {
        try
        {
            Object result = Ognl.getValue( expression, context, ( Object ) null );

            return result;
        }
        catch ( OgnlException e )
        {
            throw new ExpressionEvaluationException( expression, e );
        }
    }

    public Object assign( Object target, String property, Object value )
        throws ExpressionEvaluationException
    {
        try
        {
            Ognl.setValue( property, target, value );

            return target;
        }
        catch ( OgnlException e )
        {
            throw new ExpressionEvaluationException( property, e );
        }
    }

    protected Pattern getExpressionPattern(  )
    {
        return EXPRESSION_PATTERN;
    }
}
