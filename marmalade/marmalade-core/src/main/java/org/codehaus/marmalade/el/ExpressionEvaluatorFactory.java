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
/* Created on Mar 24, 2004 */
package org.codehaus.marmalade.el;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.reflect.UndeclaredThrowableException;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Provide factory methods to retrieve specific implementations for various
 * dynamic elements of the marmalade system.
 * 
 * @author John Casey
 */
public final class ExpressionEvaluatorFactory
{
    private static final String DEFAULT_EL_TYPE = "ognl";

    private static Map evaluators = new WeakHashMap();

    /**
     * Factory; deny construction.
     */
    public ExpressionEvaluatorFactory()
    {
    }

    /**
     * Return the default expression evaluator implementation.
     */
    public static ExpressionEvaluator getDefaultExpressionEvaluator()
    {
        return new ExpressionEvaluatorFactory().getExpressionEvaluator( DEFAULT_EL_TYPE );
    }

    /**
     * Return the expression evaluator implementation for the specified EL type.
     */
    public ExpressionEvaluator getExpressionEvaluator( String type )
    {
        // lock only for the specified type of EL...let everything else happen.
        synchronized ( type.intern() )
        {
            ExpressionEvaluator evaluator = (ExpressionEvaluator) evaluators.get( type );

            if ( evaluator == null )
            {
                String elResource = "META-INF/marmalade/el/" + type;
                ClassLoader cloader = ExpressionEvaluatorFactory.class.getClassLoader();

                InputStream res = cloader.getResourceAsStream( elResource );

                if ( res == null )
                {
                    // DO NOT cache this...it is only for emergency cases, to
                    // enable minimal functionality.
                    return new BareBonesExpressionEvaluator();
                }
                else
                {
                    String className = null;
                    BufferedReader in = null;

                    try
                    {
                        in = new BufferedReader( new InputStreamReader( res ) );
                        className = in.readLine();
                        in.close();
                    }
                    catch ( IOException e )
                    {
                    }
                    finally
                    {
                        if ( in != null )
                        {
                            try
                            {
                                in.close();
                            }
                            catch ( IOException e )
                            {
                            }
                        }
                    }

                    if ( (className != null) && (className.length() > 0) )
                    {
                        try
                        {
                            Class elClass = cloader.loadClass( className );

                            evaluator = (ExpressionEvaluator) elClass.newInstance();
                            evaluators.put( type, evaluator );
                        }
                        catch ( ClassNotFoundException e )
                        {
                        }
                        catch ( InstantiationException e )
                        {
                        }
                        catch ( IllegalAccessException e )
                        {
                        }
                        catch ( UndeclaredThrowableException e )
                        {
                        }
                    }
                }
            }

            return evaluator;
        }
    }
}