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
/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.el;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author jdcasey
 */
public class PassThroughExpressionEvaluator
    implements ExpressionEvaluator
{
    public PassThroughExpressionEvaluator()
    {
    }

    public Object evaluate( String expression, Map context, Class expectedReturnType )
        throws ExpressionEvaluationException
    {
        if ( expectedReturnType.equals( Boolean.class ) )
        {
            return Boolean.valueOf( expression );
        }
        else
        {
            return expression;
        }
    }

    public Object assign( Object target, String property, Object value ) throws ExpressionEvaluationException
    {
        throw new ExpressionEvaluationException( "PassThrough evaluator not equipped for this. "
            + "Please ensure that the ognl evaluator is on the classpath." );
    }

    public Pattern getExpressionPattern()
    {
        return Pattern.compile( ".*" );
    }
}