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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.InvocationTargetException;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author jdcasey
 */
public class BareBonesExpressionEvaluator
    extends AbstractExpressionEvaluator
{
    public static final String LEGAL_OBJECT_PROPERTY_NAME_PATTERN = "[_a-zA-Z0-9]+";

    public BareBonesExpressionEvaluator()
    {
    }

    public Object assign( Object target, String property, Object value ) throws ExpressionEvaluationException
    {
        if ( property.matches( LEGAL_OBJECT_PROPERTY_NAME_PATTERN ) )
        {
            Object result = null;

            PropertyDescriptor descriptor = findPropertyDescriptor( target, property, value );

            executePropertySet( descriptor, target, value );

            return result;
        }
        else
        {
            throw new ExpressionEvaluationException( "advanced assignment not supported by bare-bones evaluator" );
        }
    }

    public Pattern getExpressionPattern()
    {
        return AbstractExpressionEvaluator.EXPRESSION_PATTERN;
    }

    protected Object doEval( String expression, Map context, Class expectedType ) throws ExpressionEvaluationException
    {
        Object result = expression;

        if ( expression.matches( AbstractExpressionEvaluator.LITERAL_PATTERNS ) )
        {
            result = evalAsLiteral( expression, context, expectedType );
        }
        else
        {
            result = evalAsObject( expression, context, expectedType );
        }

        return result;
    }

    private Object evalAsObject( String expression, Map context, Class expectedType )
        throws ExpressionEvaluationException
    {
        Object result = null;

        String lookupKey = expression.substring( 2, expression.length() - 1 );
        Object value = context.get( lookupKey );

        if ( value != null )
        {
            if ( Boolean.class.equals( expectedType ) )
            {
                result = Boolean.valueOf( String.valueOf( value ) );
            }
            else if ( String.class.equals( expectedType ) )
            {
                result = String.valueOf( value );
            }
            else if ( expectedType.isAssignableFrom( value.getClass() ) )
            {
                result = value;
            }
            else
            {
                throw new ExpressionEvaluationException( "coercion not available in bare-bones expression evaluator" );
            }
        }

        return result;
    }

    private Object evalAsLiteral( String expression, Map context, Class expectedType )
        throws ExpressionEvaluationException
    {
        Object result = null;

        if ( Boolean.class.equals( expectedType ) )
        {
            result = Boolean.valueOf( expression );
        }
        else if ( String.class.equals( expectedType ) || Object.class.equals( expectedType ) )
        {
            result = expression.substring( 2, expression.length() - 1 );
        }
        else
        {
            throw new ExpressionEvaluationException( "coercion not available in bare-bones expression evaluator" );
        }

        return result;
    }

    private PropertyDescriptor findPropertyDescriptor( Object target, String property, Object value )
        throws ExpressionEvaluationException
    {
        BeanInfo beanInfo = null;

        try
        {
            beanInfo = Introspector.getBeanInfo( target.getClass() );
        }
        catch ( IntrospectionException e )
        {
            throw new ExpressionEvaluationException( "cannot discover target properties", e );
        }

        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        PropertyDescriptor descriptor = null;

        for ( int i = 0; i < descriptors.length; i++ )
        {
            PropertyDescriptor descriptorCandidate = descriptors[i];

            if ( descriptorCandidate.getName().equals( property )
                && descriptorCandidate.getPropertyType().isAssignableFrom( value.getClass() ) )
            {
                descriptor = descriptorCandidate;

                break;
            }
        }

        return descriptor;
    }

    private Object executePropertySet( PropertyDescriptor descriptor, Object target, Object value )
        throws ExpressionEvaluationException
    {
        if ( descriptor == null )
        {
            throw new ExpressionEvaluationException( "target property not found" );
        }

        try
        {
            return descriptor.getWriteMethod().invoke( target, new Object[] { value } );
        }
        catch ( IllegalArgumentException e )
        {
            throw new ExpressionEvaluationException( "error setting target property", e );
        }
        catch ( IllegalAccessException e )
        {
            throw new ExpressionEvaluationException( "error setting target property", e );
        }
        catch ( InvocationTargetException e )
        {
            throw new ExpressionEvaluationException( "error setting target property", e );
        }
    }
}