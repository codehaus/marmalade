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
/*

Copyright (c) 2002 John Casey. All rights reserved.

SEE licenses/cj-license.txt FOR MORE INFORMATION.

*/
/*
 * Reflector.java
 *
 * Created on November 1, 2002, 7:31 AM
 */
package org.codehaus.marmalade.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/** Utility class used to instantiate an object using reflection.
 *  This utility hides many of the gory details needed to do this.
 *
 * @author  John Casey
 */
public final class Reflector
{
    private static final Log LOG = LogFactory.getLog( Reflector.class );
    private static final String CONSTRUCTOR_METHOD_NAME = "$$CONSTRUCTOR$$";
    private static final String GET_INSTANCE_METHOD_NAME = "getInstance";
    private static HashMap classMaps = new HashMap(  );

    /** Ensure no instances of Reflector are created...this is a utility. */
    private Reflector(  )
    {
    }

    /** Create a new instance of a class, given the array of parameters...
     *  Uses constructor caching to find a constructor that matches the
     *  parameter types, either specifically (first choice) or abstractly...
     * @param theClass The class to instantiate
     * @param params The parameters to pass to the constructor
     * @return The instantiated object
     * @throws ReflectorException In case anything goes wrong here...
     */
    public static Object newInstance( Class theClass, Object[] params )
        throws ReflectorException
    {
        if ( params == null )
        {
            params = new Object[0];
        }

        Class[] paramTypes = new Class[params.length];

        for ( int i = 0, len = params.length; i < len; i++ )
        {
            paramTypes[i] = params[i].getClass(  );
        }

        try
        {
            Constructor con = getConstructor( theClass, paramTypes );

            if ( con == null )
            {
                StringBuffer buffer = new StringBuffer(  );

                buffer.append( "Constructor not found for class: " );
                buffer.append( theClass.getName(  ) );
                buffer.append( 
                    " with specified or ancestor parameter classes: " );

                for ( int i = 0; i < paramTypes.length; i++ )
                {
                    buffer.append( paramTypes[i].getName(  ) );
                    buffer.append( ',' );
                }

                buffer.setLength( buffer.length(  ) - 1 );

                throw new ReflectorException( buffer.toString(  ) );
            }

            return con.newInstance( params );
        }
        catch ( InstantiationException ex )
        {
            throw new ReflectorException( ex );
        }
        catch ( InvocationTargetException ex )
        {
            throw new ReflectorException( ex );
        }
        catch ( IllegalAccessException ex )
        {
            throw new ReflectorException( ex );
        }
    }

    /** Retrieve the singleton instance of a class, given the array of parameters...
     *  Uses constructor caching to find a constructor that matches the
     *  parameter types, either specifically (first choice) or abstractly...
     * @param theClass The class to retrieve the singleton of
     * @param initParams The parameters to pass to the constructor
     * @return The singleton object
     * @throws ReflectorException In case anything goes wrong here...
     */
    public static Object getSingleton( Class theClass, Object[] initParams )
        throws ReflectorException
    {
        Class[] paramTypes = new Class[initParams.length];

        for ( int i = 0, len = initParams.length; i < len; i++ )
        {
            paramTypes[i] = initParams[i].getClass(  );
        }

        try
        {
            Method method = getMethod( theClass, GET_INSTANCE_METHOD_NAME,
                    paramTypes );

            return method.invoke( null, initParams );
        }
        catch ( InvocationTargetException ex )
        {
            throw new ReflectorException( ex );
        }
        catch ( IllegalAccessException ex )
        {
            throw new ReflectorException( ex );
        }
    }

    /** Invoke the specified method on the specified target with the specified params...
     *
     * @param target The target of the invocation
     * @param methodName The method name to invoke
     * @param params The parameters to pass to the method invocation
     * @return The result of the method call
     * @throws ReflectorException In case of an error looking up or invoking the method.
     */
    public static Object invoke( Object target, String methodName,
        Object[] params ) throws ReflectorException
    {
        if ( params == null )
        {
            params = new Object[0];
        }

        Class[] paramTypes = new Class[params.length];

        for ( int i = 0, len = params.length; i < len; i++ )
        {
            paramTypes[i] = params[i].getClass(  );
        }

        try
        {
            Method method = getMethod( target.getClass(  ), methodName,
                    paramTypes );

            if ( method == null )
            {
                StringBuffer buffer = new StringBuffer(  );

                buffer.append( "Singleton-producing method named \'"
                    + methodName
                    + "\' not found with specified parameter classes: " );

                for ( int i = 0; i < paramTypes.length; i++ )
                {
                    buffer.append( paramTypes[i].getName(  ) );
                    buffer.append( ',' );
                }

                buffer.setLength( buffer.length(  ) - 1 );

                throw new ReflectorException( buffer.toString(  ) );
            }

            return method.invoke( target, params );
        }
        catch ( InvocationTargetException ex )
        {
            throw new ReflectorException( ex );
        }
        catch ( IllegalAccessException ex )
        {
            throw new ReflectorException( ex );
        }
    }

    public static Object getStaticField( Class targetClass, String fieldName )
        throws ReflectorException
    {
        try
        {
            Field field = targetClass.getField( fieldName );

            return field.get( null );
        }
        catch ( SecurityException e )
        {
            throw new ReflectorException( e );
        }
        catch ( NoSuchFieldException e )
        {
            throw new ReflectorException( e );
        }
        catch ( IllegalArgumentException e )
        {
            throw new ReflectorException( e );
        }
        catch ( IllegalAccessException e )
        {
            throw new ReflectorException( e );
        }
    }

    public static Object getField( Object target, String fieldName )
        throws ReflectorException
    {
        try
        {
            Class targetClass = target.getClass(  );
            Field field = targetClass.getField( fieldName );

            return field.get( target );
        }
        catch ( SecurityException e )
        {
            throw new ReflectorException( e );
        }
        catch ( NoSuchFieldException e )
        {
            throw new ReflectorException( e );
        }
        catch ( IllegalArgumentException e )
        {
            throw new ReflectorException( e );
        }
        catch ( IllegalAccessException e )
        {
            throw new ReflectorException( e );
        }
    }

    /** Invoke the specified static method with the specified params...
    *
    * @param targetClass The target class of the invocation
    * @param methodName The method name to invoke
    * @param params The parameters to pass to the method invocation
    * @return The result of the method call
    * @throws ReflectorException In case of an error looking up or invoking the method.
    */
    public static Object invokeStatic( Class targetClass, String methodName,
        Object[] params ) throws ReflectorException
    {
        if ( params == null )
        {
            params = new Object[0];
        }

        Class[] paramTypes = new Class[params.length];

        for ( int i = 0, len = params.length; i < len; i++ )
        {
            paramTypes[i] = params[i].getClass(  );
        }

        try
        {
            Method method = getMethod( targetClass, methodName, paramTypes );

            if ( method == null )
            {
                StringBuffer buffer = new StringBuffer(  );

                buffer.append( "Singleton-producing method named \'"
                    + methodName
                    + "\' not found with specified parameter classes: " );

                for ( int i = 0; i < paramTypes.length; i++ )
                {
                    buffer.append( paramTypes[i].getName(  ) );
                    buffer.append( ',' );
                }

                buffer.setLength( buffer.length(  ) - 1 );

                throw new ReflectorException( buffer.toString(  ) );
            }

            return method.invoke( null, params );
        }
        catch ( InvocationTargetException ex )
        {
            throw new ReflectorException( ex );
        }
        catch ( IllegalAccessException ex )
        {
            throw new ReflectorException( ex );
        }
    }

    /** Return the constructor, checking the cache first and storing in cache if not
     *  already there..
     *
     * @param targetClass The class to get the constructor from
     * @param params The classes of the parameters which the constructor should match.
     * @return the Constructor object that matches.
     * @throws ReflectorException In case we can't retrieve the proper constructor.
     */
    private static Constructor getConstructor( Class targetClass, Class[] params )
        throws ReflectorException
    {
        if ( LOG.isDebugEnabled(  ) )
        {
            LOG.debug( "Target class: " + targetClass );
        }

        Map constructorMap = getConstructorMap( targetClass );

        StringBuffer key = new StringBuffer( 200 );

        key.append( "(" );

        for ( int i = 0, len = params.length; i < len; i++ )
        {
            key.append( params[i].getName(  ) );
            key.append( "," );
        }

        if ( params.length > 0 )
        {
            key.setLength( key.length(  ) - 1 );
        }

        key.append( ")" );

        if ( LOG.isDebugEnabled(  ) )
        {
            LOG.debug( "searching for key: \'" + key + "\'" );
        }

        Constructor constructor = null;

        String paramKey = key.toString(  );

        synchronized ( paramKey.intern(  ) )
        {
            constructor = ( Constructor ) constructorMap.get( paramKey );

            if ( constructor == null )
            {
                Constructor[] cands = targetClass.getConstructors(  );

                for ( int i = 0, len = cands.length; i < len; i++ )
                {
                    Class[] types = cands[i].getParameterTypes(  );

                    if ( params.length != types.length )
                    {
                        continue;
                    }

                    for ( int j = 0, len2 = params.length; j < len2; j++ )
                    {
                        if ( !types[j].isAssignableFrom( params[j] ) )
                        {
                            continue;
                        }
                    }

                    // we got it, so store it!
                    constructor = cands[i];
                    constructorMap.put( paramKey, constructor );
                }
            }
        }

        if ( constructor == null )
        {
            throw new ReflectorException( 
                "Error retrieving constructor object for: "
                + targetClass.getName(  ) + paramKey );
        }

        return constructor;
    }

    /** Return the method, checking the cache first and storing in cache if not
     *  already there..
     *
     * @param targetClass The class to get the method from
     * @param params The classes of the parameters which the method should match.
     * @return the Method object that matches.
     * @throws ReflectorException In case we can't retrieve the proper method.
     */
    private static Method getMethod( Class targetClass, String methodName,
        Class[] params ) throws ReflectorException
    {
        Map methodMap = getMethodMap( targetClass, methodName );

        StringBuffer key = new StringBuffer( 200 );

        key.append( "(" );

        for ( int i = 0, len = params.length; i < len; i++ )
        {
            key.append( params[i].getName(  ) );
            key.append( "," );
        }

        key.append( ")" );

        Method method = null;

        String paramKey = key.toString(  );

        synchronized ( paramKey.intern(  ) )
        {
            method = ( Method ) methodMap.get( paramKey );

            if ( method == null )
            {
                Method[] cands = targetClass.getMethods(  );

                for ( int i = 0, len = cands.length; i < len; i++ )
                {
                    String name = cands[i].getName(  );

                    if ( !methodName.equals( name ) )
                    {
                        continue;
                    }

                    Class[] types = cands[i].getParameterTypes(  );

                    if ( params.length != types.length )
                    {
                        continue;
                    }

                    for ( int j = 0, len2 = params.length; j < len2; j++ )
                    {
                        if ( !types[j].isAssignableFrom( params[j] ) )
                        {
                            continue;
                        }
                    }

                    // we got it, so store it!
                    method = cands[i];
                    methodMap.put( paramKey, method );
                }
            }
        }

        if ( method == null )
        {
            throw new ReflectorException( 
                "Error retrieving method object for: "
                + targetClass.getName(  ) + "." + methodName + paramKey );
        }

        return method;
    }

    /** Retrieve the cache of constructors for the specified class.
     * @param theClass the class to lookup.
     * @return The cache of constructors.
     * @throws ReflectorException in case of a lookup error.
     */
    private static Map getConstructorMap( Class theClass )
        throws ReflectorException
    {
        return getMethodMap( theClass, CONSTRUCTOR_METHOD_NAME );
    }

    /** Retrieve the cache of methods for the specified class and method name.
     * @param theClass the class to lookup.
     * @param methodName The name of the method to lookup.
     * @return The cache of constructors.
     * @throws ReflectorException in case of a lookup error.
     */
    private static Map getMethodMap( Class theClass, String methodName )
        throws ReflectorException
    {
        Map methodMap = null;

        if ( theClass == null )
        {
            if ( LOG.isDebugEnabled(  ) )
            {
                LOG.debug( "class was null. Aborting." );
            }

            return null;
        }

        String className = theClass.getName(  );

        synchronized ( className.intern(  ) )
        {
            Map classMethods = ( Map ) classMaps.get( className );

            if ( classMethods == null )
            {
                classMethods = new HashMap(  );
                methodMap = new HashMap(  );
                classMethods.put( methodName, methodMap );

                classMaps.put( className, classMethods );
            }
            else
            {
                String key = className + "::" + methodName;

                synchronized ( key.intern(  ) )
                {
                    methodMap = ( Map ) classMethods.get( methodName );

                    if ( methodMap == null )
                    {
                        methodMap = new HashMap(  );
                        classMethods.put( methodName, methodMap );
                    }
                }
            }
        }

        return methodMap;
    }
}
