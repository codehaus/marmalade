/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.TagInstantiationException;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.util.Reflector;
import org.codehaus.marmalade.util.ReflectorException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author jdcasey
 */
public class LooseMarmaladeTagFactory
    implements LateBoundTagFactory
{
    public static final Reflector REFLECTOR = new Reflector();

    private final Class tagClass;

    private final List constructorArgs;

    private final Map properties;

    private final ExpressionEvaluator el;

    private final MarmaladeTagInfo tagInfo;

    public LooseMarmaladeTagFactory( MarmaladeTagInfo tagInfo, Class tagClass, List constructorArgs, Map properties, ExpressionEvaluator el )
    {
        this.tagInfo = tagInfo;
        this.tagClass = tagClass;
        this.constructorArgs = constructorArgs;
        this.properties = properties;
        this.el = el;
    }

    public MarmaladeTag newTag() throws TagInstantiationException
    {
        MarmaladeTag tag = constructTag();

        configureTag( tag );

        return tag;
    }

    private void configureTag( MarmaladeTag tag ) throws TagInstantiationException
    {
        for ( Iterator it = properties.keySet().iterator(); it.hasNext(); )
        {
            String property = (String) it.next();
            Object value = properties.get( property );

            try
            {
                if(el == null) {
                    throw new TagInstantiationException(tag.getTagInfo(), "cannot assign properties when expression evaluator is null");
                }
                el.assign( tag, property, value );
            }
            catch ( ExpressionEvaluationException e )
            {
                throw new LateBoundTagPropertyException( tagInfo, tagClass, property, value, e );
            }
        }
    }

    private MarmaladeTag constructTag() throws TagInstantiationException
    {
        Class[] paramClasses = new Class[constructorArgs.size()];
        Object[] constructorParams = new Object[constructorArgs.size()];

        for ( int i = 0; i < constructorArgs.size(); i++ )
        {
            Object arg = constructorArgs.get( i );

            constructorParams[i] = arg;
            paramClasses[i] = constructorParams[i].getClass();
        }

        MarmaladeTag tag = null;

        try
        {
            Constructor tagConstructor = REFLECTOR.getConstructor( tagClass, paramClasses );

            tag = (MarmaladeTag) tagConstructor.newInstance( constructorParams );
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

        return tag;
    }
}