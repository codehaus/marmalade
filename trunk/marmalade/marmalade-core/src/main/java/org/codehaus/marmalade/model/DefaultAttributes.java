package org.codehaus.marmalade.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.ModelBuilderAttribute;
import org.codehaus.marmalade.metamodel.ModelBuilderAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jdcasey
 */
public class DefaultAttributes implements MarmaladeAttributes
{
    private ExpressionEvaluator el;
    private ModelBuilderAttributes attributes;
    
    private transient Set attributesSet; 

    public DefaultAttributes( ExpressionEvaluator el,
        ModelBuilderAttributes attributes )
    {
        this.el = el;
        this.attributes = ( attributes != null ) ? ( attributes )
                                                 : ( new DefaultRawAttributes(  ) );
    }

    public ExpressionEvaluator getExpressionEvaluator(  )
    {
        return el;
    }

    public Object getValue( String name, MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        return _getValue( name, Object.class,
            context.unmodifiableVariableMap(  ), null );
    }

    public Object getValue( String name, MarmaladeExecutionContext context,
        Object defaultVal ) throws ExpressionEvaluationException
    {
        return _getValue( name, Object.class,
            context.unmodifiableVariableMap(  ), defaultVal );
    }

    public Object getValue( String name, Class type,
        MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        return _getValue( name, type, context.unmodifiableVariableMap(  ), null );
    }

    public Object getValue( String name, Class type,
        MarmaladeExecutionContext context, Object defaultVal )
        throws ExpressionEvaluationException
    {
        return _getValue( name, type, context.unmodifiableVariableMap(  ),
            defaultVal );
    }

    private Object _getValue( String name, Class type, Map context,
        Object defaultVal ) throws ExpressionEvaluationException
    {
        String expression = attributes.getValue( name );
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

    public Iterator iterator() {
        synchronized(this) {
            if(attributesSet == null) {
                attributesSet = new HashSet();
                for (Iterator it = attributes.iterator(); it.hasNext();) {
                    ModelBuilderAttribute raw = (ModelBuilderAttribute) it.next();
                    attributesSet.add(new DefaultAttribute(raw, el));
                }
                attributesSet = Collections.unmodifiableSet(attributesSet);
            }
        }
        
        return attributesSet.iterator();
    }
    
}
