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
package org.codehaus.marmalade.el.commonsEl;

import org.apache.commons.el.ExpressionEvaluatorImpl;
import org.codehaus.marmalade.el.AbstractExpressionEvaluator;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.util.Reflector;
import org.codehaus.marmalade.util.ReflectorException;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * @author jdcasey
 */
public class CommonsElExpressionEvaluator extends AbstractExpressionEvaluator
{
    public static final String BOOLEAN_PATTERN = "true|false";
    
    private static final String DIGIT_STRING_PATTERN = "[0-9]+";
    private static final String POSITIVE_NEGATIVE_PATTERN = "[-+]?";
    public static final String ROUND_NUMBER_PATTERN = POSITIVE_NEGATIVE_PATTERN + DIGIT_STRING_PATTERN;
    public static final String FRACTIONAL_NUMBER_PATTERN = POSITIVE_NEGATIVE_PATTERN + DIGIT_STRING_PATTERN + "." + DIGIT_STRING_PATTERN;
    
    private ExpressionEvaluatorImpl elImpl = new ExpressionEvaluatorImpl( true );
    private Reflector reflector = new Reflector(  );

    public CommonsElExpressionEvaluator(  )
    {
    }

    public Object doEval( String expression, Map context, Class expectedType )
        throws ExpressionEvaluationException
    {
        Class realExpectedType = expectedType;
        if(expectedType.equals(Object.class)) {
            // try to figure out what we can intelligently do to interpret this value.
            if(expression.matches(BOOLEAN_PATTERN)) {
                realExpectedType = Boolean.class;
            }
            else if(expression.matches(ROUND_NUMBER_PATTERN)) {
                realExpectedType = Long.class;
            }
            else if(expression.matches(FRACTIONAL_NUMBER_PATTERN)) {
                realExpectedType = Double.class;
            }
        }
        
        Object result = eval( expression, context, realExpectedType );

        return result;
    }

    public Object assign( Object targetObject, String propertyName, Object value )
        throws ExpressionEvaluationException
    {
        Object result = null;

        if ( targetObject == null )
        {
            throw new ExpressionEvaluationException( 
                "Cannot set property on null target." );
        }
        else if ( ( propertyName == null ) || ( propertyName.length(  ) < 1 ) )
        {
            throw new ExpressionEvaluationException( 
                "Cannot set empty property." );
        }
        else
        {
            Object target = targetObject;
            String property = propertyName;

            int lastDot = property.lastIndexOf( '.' );

            if ( lastDot > -1 )
            {
                String expr = "${target." + property.substring( 0, lastDot )
                    + "}";
                Map ctx = new TreeMap(  );

                ctx.put( "target", target );
                target = eval( expr, ctx, Object.class );
                property = property.substring( lastDot + 1 );
            }

            result = set( target, property, value );
        }

        return result;
    }

    private Object set( Object target, String property, Object value )
        throws ExpressionEvaluationException
    {
        Object result = null;

        try
        {
            Method method = findMethod( target, property, value );

            if ( method != null )
            {
                result = method.invoke( target, new Object[] { value } );
            }
            else
            {
                Field field = target.getClass(  ).getField( property );
                int fieldModifiers = field.getModifiers(  );

                if ( Modifier.isPublic( fieldModifiers )
                    && !Modifier.isFinal( fieldModifiers )
                    && field.getType(  ).isAssignableFrom( value.getClass(  ) ) )
                {
                    field.set( target, value );
                    result = value;
                }
                else
                {
                    throw new ExpressionEvaluationException( 
                        "Cannot set property: \'" + property + "\' on target: "
                        + target );
                }
            }
        }
        catch ( IllegalArgumentException e )
        {
            throw new ExpressionEvaluationException( "Error assigning value to property field.",
                e );
        }
        catch ( IllegalAccessException e )
        {
            throw new ExpressionEvaluationException( "Error assigning value to property field.",
                e );
        }
        catch ( SecurityException e )
        {
            throw new ExpressionEvaluationException( "Error assigning value to property field.",
                e );
        }
        catch ( NoSuchFieldException e )
        {
            throw new ExpressionEvaluationException( "Property not found: \'"
                + property + "\' on target: " + target, e );
        }
        catch ( InvocationTargetException e )
        {
            throw new ExpressionEvaluationException( "Error assigning value via property method.",
                e );
        }

        return result;
    }

    private Method findMethod( Object target, String property, Object value )
    {
        StringBuffer propNameBuffer = new StringBuffer( property.length(  ) + 3 );

        propNameBuffer.append( "set" )
                      .append( Character.toUpperCase( property.charAt( 0 ) ) )
                      .append( property.substring( 1 ) );

        String methodName = propNameBuffer.toString(  );
        Class targetClass = target.getClass(  );

        Method method = null;

        if ( value != null )
        {
            Class[] paramClasses = { value.getClass(  ) };

            try
            {
                method = reflector.getMethod( targetClass, methodName,
                        paramClasses );
                method = targetClass.getMethod( methodName, paramClasses );
            }
            catch ( NoSuchMethodException e )
            {
                // Ignore and return null, so we can move on to try field property access.
            }
            catch ( ReflectorException e )
            {
                // Ignore and return null.
            }
        }
        else
        {
            try
            {
                method = reflector.getMethod( targetClass, methodName,
                        new Class[0] );
            }
            catch ( ReflectorException e )
            {
                // Ignore and return null.
            }
        }

        return method;
    }

    private Object eval( String expression, Map context,
        Class expectedReturnType )
        throws ExpressionEvaluationException
    {
        try
        {
            Object result = elImpl.evaluate( expression, expectedReturnType,
                    new ElVarResolver( context ), new ElFuncMapper(  ) );

            if ( ( result != null )
                && !expectedReturnType.isAssignableFrom( result.getClass(  ) ) )
            {
                throw new ExpressionEvaluationException( 
                    "Result of evaluation is not of type "
                    + expectedReturnType.getName(  ) );
            }
            else
            {
                return result;
            }
        }
        catch ( ELException e )
        {
            throw new ExpressionEvaluationException( expression, e );
        }
    }

    private static final class ElVarResolver implements VariableResolver
    {
        private Map context;

        ElVarResolver( Map context )
        {
            this.context = context;
        }

        public Object resolveVariable( String name )
            throws ELException
        {
            return context.get( name );
        }
    }

    private static final class ElFuncMapper implements FunctionMapper
    {
        ElFuncMapper(  )
        {
        }

        public Method resolveFunction( String arg0, String arg1 )
        {
            return null;
        }
    }
}
