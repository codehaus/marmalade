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
/* Created on Jun 23, 2004 */
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MetaAttribute;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

/**
 * @author jdcasey
 */
public class DefaultAttribute implements MarmaladeAttribute
{
    private ExpressionEvaluator el;
    private MetaAttribute attribute;

    public DefaultAttribute( MetaAttribute attribute,
        ExpressionEvaluator el )
    {
        this.attribute = attribute;
        this.el = el;
    }

    public String getNamespace(  )
    {
        return attribute.getNamespace(  );
    }

    public String getName(  )
    {
        return attribute.getName(  );
    }

    public Object getValue( Class returnType, MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        return _getValue( returnType, context );
    }

    public Object getValue( MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        return _getValue( Object.class, context );
    }

    private Object _getValue( Class returnType,
        MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        String expression = attribute.getValue(  );
        Object result = expression;

        if ( ( expression != null ) && ( expression.length(  ) > 0 ) )
        {
            result = el.evaluate( expression,
                    context.unmodifiableVariableMap(  ), returnType );
        }

        return result;
    }
}
