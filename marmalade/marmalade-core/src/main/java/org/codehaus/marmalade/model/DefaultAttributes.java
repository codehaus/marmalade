
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.ModelBuilderAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

import java.util.Map;

/**
 * @author jdcasey
 */
public class DefaultAttributes implements MarmaladeAttributes
{
    private ExpressionEvaluator el;
    private ModelBuilderAttributes attributes;

    public DefaultAttributes( ExpressionEvaluator el,
        ModelBuilderAttributes attributes )
    {
        this.el = el;
        this.attributes = (attributes != null)?(attributes):(new DefaultRawAttributes());
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

        /*
            if(result != null && !type.isAssignableFrom(result.getClass())) {
              throw new ExpressionEvaluationException(
                "Expression: \'" +
                expression +
                "\' for attribute: " +
                name +
                " returned result of type: " +
                result.getClass().getName() +
                "; expected: " +
                type.getName()
              );
            }
        */
        if ( result == null )
        {
            result = defaultVal;
        }

        return result;
    }

    private static final class DefaultAttribute
    {
        private String namespace;
        private String name;
        private String value;

        DefaultAttribute( String namespace, String name, String value )
        {
            this.namespace = namespace;
            this.name = name;
            this.value = value;
        }

        /**
         * @return Returns the name.
         */
        public String getName(  )
        {
            return name;
        }

        /**
         * @return Returns the namespace.
         */
        public String getNamespace(  )
        {
            return namespace;
        }

        /**
         * @return Returns the value.
         */
        public String getValue(  )
        {
            return value;
        }
    }
}
