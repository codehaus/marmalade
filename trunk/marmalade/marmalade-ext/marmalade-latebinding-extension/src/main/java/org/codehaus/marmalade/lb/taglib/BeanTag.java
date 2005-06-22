/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.lb.model.beans.BeanBoundTagFactory;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.parsing.ParserHint;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.TagExecutionException;

import java.lang.reflect.Method;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class BeanTag
    extends AbstractMarmaladeTag
    implements MethodParamOwner, ConstructorArgOwner, PropertyOwner
{
    public static final String BEAN_CLASS_ATTRIBUTE = "class";

    public static final String METHOD_NAME_ATTRIBUTE = "method";

    private List constructorArgs = new LinkedList();

    private Map properties = new TreeMap();

    private List methodParams = new LinkedList();

    protected void doExecute( MarmaladeExecutionContext context ) throws MarmaladeExecutionException
    {
        String name = (String) requireTagAttribute( DefinitionTagConstants.TAG_NAME_ATTRIBUTE, String.class, context );

        String tagClassName = (String) requireTagAttribute( BEAN_CLASS_ATTRIBUTE, String.class, context );

        ClassLoader cloader = Thread.currentThread().getContextClassLoader();

        Class tagClass = null;

        try
        {
            tagClass = cloader.loadClass( tagClassName );
        }
        catch ( ClassNotFoundException e )
        {
            throw new TagExecutionException( getTagInfo(), "Cannot find embedded bean class.", e );
        }

        String methodName = (String) requireTagAttribute( METHOD_NAME_ATTRIBUTE, String.class, context );

        Method executeMethod = findMethod( tagClass, methodName );

        BeanBoundTagFactory factory = new BeanBoundTagFactory( getTagInfo(), tagClass, executeMethod, constructorArgs, properties,
            methodParams, getExpressionEvaluator() );

        LateBoundTagLibraryOwner parent = (LateBoundTagLibraryOwner) requireParent( LateBoundTagLibraryOwner.class );

        parent.registerTag( name, new ParserHint(), factory );
    }

    private Method findMethod( Class tagClass, String methodName ) throws MarmaladeExecutionException
    {
        Method executeMethod = null;

        try
        {
            Class[] ctxParams = { MarmaladeExecutionContext.class };

            executeMethod = tagClass.getMethod( methodName, ctxParams );
        }
        catch ( SecurityException e )
        {
            throw new TagExecutionException( getTagInfo(), "Cannot access embedded bean method with MarmaladeExecutionContext parameter mapped to tag execution.", e );
        }
        catch ( NoSuchMethodException e )
        {
            throw new TagExecutionException( getTagInfo(), "Cannot find embedded bean method with MarmaladeExecutionContext parameter mapped to tag execution.", e );
        }

        if ( executeMethod == null )
        {
            try
            {
                Class[] ctxParams = { Map.class };

                executeMethod = tagClass.getMethod( methodName, ctxParams );
            }
            catch ( SecurityException e )
            {
                throw new TagExecutionException( getTagInfo(), "Cannot access embedded bean method with java.util.Map parameter mapped to tag execution.", e );
            }
            catch ( NoSuchMethodException e )
            {
                throw new TagExecutionException( getTagInfo(), "Cannot access embedded bean method with java.util.Map parameter mapped to tag execution.", e );
            }
        }

        if ( executeMethod == null )
        {
            try
            {
                Class[] ctxParams = {};

                executeMethod = tagClass.getMethod( methodName, ctxParams );
            }
            catch ( SecurityException e )
            {
                throw new TagExecutionException( getTagInfo(), "Cannot access embedded bean method with no parameters mapped to tag execution.", e );
            }
            catch ( NoSuchMethodException e )
            {
                throw new TagExecutionException( getTagInfo(), "Cannot access embedded bean method with no parameters mapped to tag execution.", e );
            }
        }

        return executeMethod;
    }

    public void addConstructorArg( Object constructorArg )
    {
        constructorArgs.add( constructorArg );
    }

    public void addMethodParameter( Object methodParam )
    {
        methodParams.add( methodParam );
    }

    public void addProperty( String property, Object value )
    {
        properties.put( property, value );
    }
}