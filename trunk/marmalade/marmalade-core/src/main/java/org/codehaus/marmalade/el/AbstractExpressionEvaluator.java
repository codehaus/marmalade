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
/* Created on May 10, 2004 */
package org.codehaus.marmalade.el;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jdcasey
 */
public abstract class AbstractExpressionEvaluator implements ExpressionEvaluator
{
    
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile( 
            "\\$\\{.*\\}" );
    private static final String LITERAL_PATTERNS = "[0-9]+[idfblhIDFBLH]?|true|false|0x[0-9]+[bB]?";

    protected AbstractExpressionEvaluator(  )
    {
    }

    public Object evaluate( String expression, Map context,
        Class expectedReturnType )
        throws ExpressionEvaluationException
    {
        Object result = null;
        Matcher matcher = getExpressionPattern(  ).matcher( expression );

        if ( matcher.matches(  ) || expression.matches( LITERAL_PATTERNS ) )
        {
            result = doEval( expression, context, expectedReturnType );
        }
        else
        {
            matcher.reset(  );

            StringBuffer resultBuffer = new StringBuffer(  );

            while ( matcher.find(  ) )
            {
                String expr = matcher.group(  );
                Object exprResult = doEval( expr, context, String.class );

                matcher.appendReplacement( resultBuffer,
                    String.valueOf( exprResult ) );
            }

            matcher.appendTail( resultBuffer );

            result = resultBuffer.toString(  );
        }

        if ( ( result != null )
            && !expectedReturnType.isAssignableFrom( result.getClass(  ) ) )
        {
            throw new ExpressionEvaluationException( "Result: \'" + result
                + "\' of expression: " + expression + " is of type: "
                + result.getClass(  ).getName(  ) + " not of type: "
                + expectedReturnType );
        }

        return result;
    }

    protected Pattern getExpressionPattern(  ) {
        return EXPRESSION_PATTERN;
    }

    protected abstract Object doEval( String expression, Map context,
        Class expectedType ) throws ExpressionEvaluationException;
}
