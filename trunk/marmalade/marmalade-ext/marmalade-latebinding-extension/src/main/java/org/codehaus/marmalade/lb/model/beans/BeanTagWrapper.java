/* Created on Aug 21, 2004 */
package org.codehaus.marmalade.lb.model.beans;

import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Map;

/**
 * @author jdcasey
 */
public class BeanTagWrapper
    extends AbstractMarmaladeTag
{
    private final Object bean;

    private final Method executeMethod;

    private final List methodParams;

    private final ExpressionEvaluator el;

    private Object returnValue;

    private String var;

    public BeanTagWrapper( Object bean, Method executeMethod, List methodParams, ExpressionEvaluator el )
    {
        this.bean = bean;
        this.executeMethod = executeMethod;
        this.methodParams = methodParams;
        this.el = el;
    }

    public void setReturnValueVariable( String var )
    {
        this.var = var;
    }

    protected void doExecute( MarmaladeExecutionContext context ) throws MarmaladeExecutionException
    {
        Map ctxMap = context.unmodifiableVariableMap();

        Object[] params = new Object[methodParams.size()];

        for ( int i = 0; i < params.length; i++ )
        {
            Object value = methodParams.get( i );

            if ( value instanceof String )
            {
                value = el.evaluate( (String) value, ctxMap, Object.class );
            }

            params[i] = value;
        }

        try
        {
            this.returnValue = executeMethod.invoke( bean, params );

            if ( var != null )
            {
                context.setVariable( var, returnValue );
            }
        }
        catch ( IllegalArgumentException e )
        {
            throw new MarmaladeExecutionException( e );
        }
        catch ( IllegalAccessException e )
        {
            throw new MarmaladeExecutionException( e );
        }
        catch ( InvocationTargetException e )
        {
            throw new MarmaladeExecutionException( e );
        }
    }

    public Object getReturnValue()
    {
        return returnValue;
    }
}