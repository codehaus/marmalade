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
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MetaAttribute;
import org.codehaus.marmalade.metamodel.MetaAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author jdcasey
 */
public class DefaultAttributes implements MarmaladeAttributes
{
    private ExpressionEvaluator el;
    private MetaAttributes attributes;
    private transient Set attributesSet;

    public DefaultAttributes( MetaAttributes attributes )
    {
        this.attributes = ( attributes != null ) ? ( attributes )
                                                 : ( new DefaultRawAttributes(  ) );
    }

    public DefaultAttributes(  )
    {
        this.attributes = new DefaultRawAttributes(  );
    }

    public void setExpressionEvaluator( ExpressionEvaluator el )
    {
        this.el = el;
    }

    public ExpressionEvaluator getExpressionEvaluator(  )
    {
        return el;
    }

    public Object getValue( String name, MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        return _getValue( null, name, Object.class,
            context.unmodifiableVariableMap(  ), null );
    }

    public Object getValue( String namespace, String name,
        MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        return _getValue( null, name, Object.class,
            context.unmodifiableVariableMap(  ), null );
    }

    public Object getValue( String name, MarmaladeExecutionContext context,
        Object defaultVal ) throws ExpressionEvaluationException
    {
        return _getValue( null, name, Object.class,
            context.unmodifiableVariableMap(  ), defaultVal );
    }

    public Object getValue( String namespace, String name,
        MarmaladeExecutionContext context, Object defaultVal )
        throws ExpressionEvaluationException
    {
        return _getValue( null, name, Object.class,
            context.unmodifiableVariableMap(  ), defaultVal );
    }

    public Object getValue( String name, Class type,
        MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        return _getValue( null, name, type,
            context.unmodifiableVariableMap(  ), null );
    }

    public Object getValue( String namespace, String name, Class type,
        MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        return _getValue( null, name, type,
            context.unmodifiableVariableMap(  ), null );
    }

    public Object getValue( String name, Class type,
        MarmaladeExecutionContext context, Object defaultVal )
        throws ExpressionEvaluationException
    {
        return _getValue( null, name, type,
            context.unmodifiableVariableMap(  ), defaultVal );
    }

    public Object getValue( String namespace, String name, Class type,
        MarmaladeExecutionContext context, Object defaultVal )
        throws ExpressionEvaluationException
    {
        return _getValue( null, name, type,
            context.unmodifiableVariableMap(  ), defaultVal );
    }

    private Object _getValue( String namespace, String name, Class type,
        Map context, Object defaultVal )
        throws ExpressionEvaluationException
    {
        String expression = attributes.getValue( namespace, name );
        Object result = null;

        if ( ( expression != null ) && ( expression.length(  ) > 0 ) )
        {
            if ( el != null )
            {
                result = el.evaluate( expression, context, type );
            }
            else
            {
                result = expression;
            }
        }

        if ( result == null )
        {
            result = defaultVal;
        }

        return result;
    }

    public Iterator iterator(  )
    {
        synchronized ( this )
        {
            if ( attributesSet == null )
            {
                attributesSet = new HashSet(  );

                for ( Iterator it = attributes.iterator(  ); it.hasNext(  ); )
                {
                    MetaAttribute raw = ( MetaAttribute ) it.next(  );

                    attributesSet.add( new DefaultAttribute( raw, el ) );
                }

                attributesSet = Collections.unmodifiableSet( attributesSet );
            }
        }

        return attributesSet.iterator(  );
    }
}
