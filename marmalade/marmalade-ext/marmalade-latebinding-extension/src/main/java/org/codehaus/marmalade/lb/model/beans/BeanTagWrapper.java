/* Created on Aug 21, 2004 */
package org.codehaus.marmalade.lb.model.beans;

import org.codehaus.marmalade.el.BareBonesExpressionEvaluator;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class BeanTagWrapper
    extends AbstractMarmaladeTag
{
    public static final String CONTEXT_PARAM_VARIABLE = "context";
    
    public static final String CONTEXT_MAP_PARAM_VARIABLE = "contextMap";
    
    private final Object bean;

    private final Method executeMethod;

    private final List methodParams;

    private ExpressionEvaluator el;

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
        
        Map elMap = new TreeMap(ctxMap);
        elMap.put(CONTEXT_PARAM_VARIABLE, context);
        elMap.put(CONTEXT_MAP_PARAM_VARIABLE, ctxMap);

        Object[] params = new Object[methodParams.size()];

        if(el == null) {
            el = new BareBonesExpressionEvaluator();
        }
        
        for ( int i = 0; i < params.length; i++ )
        {
            Object value = methodParams.get( i );

            if ( value instanceof String )
            {
                value = el.evaluate( (String) value, elMap, Object.class );
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
            throw new MarmaladeExecutionException( e.getTargetException() );
        }
    }

    public Object getReturnValue()
    {
        return returnValue;
    }
}