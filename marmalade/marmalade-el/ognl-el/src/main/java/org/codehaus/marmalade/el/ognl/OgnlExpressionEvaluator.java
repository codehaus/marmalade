
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

/* Created on Mar 26, 2004 */
package org.codehaus.marmalade.el.ognl;

import java.util.Map;
import java.util.regex.Pattern;

import ognl.Ognl;
import ognl.OgnlException;

import org.codehaus.marmalade.el.AbstractExpressionEvaluator;
import org.codehaus.marmalade.el.ExpressionEvaluationException;

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
