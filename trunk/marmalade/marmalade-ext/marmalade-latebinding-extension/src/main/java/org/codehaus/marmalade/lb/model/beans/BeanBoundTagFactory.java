/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb.model.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.lb.model.LateBoundTagFactory;
import org.codehaus.marmalade.lb.model.LateBoundTagPropertyException;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.TagInstantiationException;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.util.Reflector;
import org.codehaus.marmalade.util.ReflectorException;

/**
 * @author jdcasey
 */
public class BeanBoundTagFactory
    implements LateBoundTagFactory
{
    public static final Reflector REFLECTOR = new Reflector();

    private final Class beanClass;

    private final List constructorArgs;

    private final List methodParams;

    private final Map properties;

    private final ExpressionEvaluator el;

    private final Method executeMethod;

    private String var;

    private final MarmaladeTagInfo tagInfo;

    public BeanBoundTagFactory( MarmaladeTagInfo tagInfo, Class beanClass, Method executeMethod, List constructorArgs, Map properties,
        List methodParams, ExpressionEvaluator el )
    {
        this.tagInfo = tagInfo;
        this.beanClass = beanClass;
        this.executeMethod = executeMethod;
        this.constructorArgs = constructorArgs;
        this.properties = properties;
        this.methodParams = methodParams;
        this.el = el;
    }

    public void setVar( String var )
    {
        this.var = var;
    }

    public MarmaladeTag newTag() throws TagInstantiationException
    {
        Object bean = constructBean();

        configureBean( bean );

        BeanTagWrapper wrapper = new BeanTagWrapper( bean, executeMethod, methodParams, el );

        if ( var != null )
        {
            wrapper.setReturnValueVariable( var );
        }

        return wrapper;
    }

    private void configureBean( Object bean ) throws TagInstantiationException
    {
        for ( Iterator it = properties.keySet().iterator(); it.hasNext(); )
        {
            String property = (String) it.next();
            Object value = properties.get( property );

            try
            {
                if(el == null) {
                    throw new TagInstantiationException(tagInfo, "cannot assign properties when expression evaluator is null");
                }
                el.assign( bean, property, value );
            }
            catch ( ExpressionEvaluationException e )
            {
                throw new LateBoundTagPropertyException( tagInfo, beanClass, property, value, e );
            }
        }
    }

    private Object constructBean() throws TagInstantiationException
    {
        Class[] paramClasses = new Class[constructorArgs.size()];
        Object[] constructorParams = new Object[constructorArgs.size()];

        for ( int i = 0; i < constructorArgs.size(); i++ )
        {
            Object arg = constructorArgs.get( i );

            constructorParams[i] = arg;
            paramClasses[i] = constructorParams[i].getClass();
        }

        Object bean = null;

        try
        {
            Constructor tagConstructor = REFLECTOR.getConstructor( beanClass, paramClasses );

            bean = tagConstructor.newInstance( constructorParams );
        }
        catch ( ReflectorException e )
        {
            throw new TagInstantiationException( tagInfo, e );
        }
        catch ( IllegalArgumentException e )
        {
            throw new TagInstantiationException( tagInfo, e );
        }
        catch ( InstantiationException e )
        {
            throw new TagInstantiationException( tagInfo, e );
        }
        catch ( IllegalAccessException e )
        {
            throw new TagInstantiationException( tagInfo, e );
        }
        catch ( InvocationTargetException e )
        {
            throw new TagInstantiationException( tagInfo, e );
        }

        return bean;
    }
}