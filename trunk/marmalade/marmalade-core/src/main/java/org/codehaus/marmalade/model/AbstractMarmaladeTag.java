
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

/* Created on Mar 24, 2004 */
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.ConfigurationException;
import org.codehaus.marmalade.runtime.IllegalAncestorException;
import org.codehaus.marmalade.runtime.IllegalParentException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/** Base class implementing common marmalade tag features.
 *
 * @author John Casey
 */
public abstract class AbstractMarmaladeTag implements MarmaladeTag
{
    public static final String MARMALADE_EL_PI_NAMESPACE = "marmalade-el";
    public static final String MARMALADE_EL_ATTRIBUTE = "marmalade:el";
    private ExpressionEvaluator el;
    private MarmaladeAttributes attributes;
    private MarmaladeTagInfo tagInfo;
    private boolean childrenProcessed = false;
    private List children = new ArrayList(  );
    private MarmaladeTag parent;

    protected AbstractMarmaladeTag( MarmaladeTagInfo tagInfo )
    {
        this.tagInfo = tagInfo;
        this.el = tagInfo.getExpressionEvaluator(  );
        this.attributes = new DefaultAttributes( el, tagInfo.getAttributes(  ) );
    }

    protected final MarmaladeTagInfo getTagInfo(  )
    {
        return tagInfo;
    }

    public final void setParent( MarmaladeTag parent )
    {
        this.parent = parent;
    }

    public final void addChild( MarmaladeTag child )
    {
        children.add( child );
    }

    public final MarmaladeTag getParent(  )
    {
        return parent;
    }

    // --------------------- CONCRETE IMPLEMENTATIONS CAN OVERRIDE THESE --------------------- //
    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
    }

    protected void doValidate( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
    }

    protected boolean alwaysProcessChildren(  )
    {
        return true;
    }

    protected void doReset(  )
    {
    }

    protected boolean shouldAddChild( MarmaladeTag child )
    {
        return true;
    }

    // ------------------ MARMALADE TAG IMPLEMENTATION DETAILS ------------------ //
    public final void execute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        doValidate( context );
        doExecute( context );

        if ( !childrenProcessed && alwaysProcessChildren(  ) )
        {
            processChildren( context );
        }

        reset(  );
    }

    protected final void resetChildrenProcessedFlag(  )
    {
        this.childrenProcessed = false;
    }

    public void processChildren( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        try
        {
            for ( Iterator it = children.iterator(  ); it.hasNext(  ); )
            {
                MarmaladeTag child = ( MarmaladeTag ) it.next(  );

                child.execute( context );
            }
        }
        finally
        {
            childrenProcessed = true;
        }
    }

    protected final List children(  )
    {
        return Collections.unmodifiableList( children );
    }

    protected final void reset(  )
    {
        doReset(  );
        childrenProcessed = false;
    }

    public final MarmaladeAttributes getAttributes(  )
    {
        return attributes;
    }

    public final ExpressionEvaluator getExpressionEvaluator(  )
        throws ConfigurationException
    {
        return el;
    }

    public final Object getBody( MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        return _getBody( context, Object.class );
    }

    public final Object getBody( MarmaladeExecutionContext context,
        Class targetType ) throws ExpressionEvaluationException
    {
        return _getBody( context, targetType );
    }

    private Object _getBody( MarmaladeExecutionContext context, Class targetType )
        throws ExpressionEvaluationException
    {
        String expression = tagInfo.getText(  );
        Object result = null;

        if ( ( expression != null ) && ( expression.length(  ) > 0 ) )
        {
            if ( el == null )
            {
                if ( targetType.isAssignableFrom( String.class ) )
                {
                    result = expression;
                }
                else
                {
                    throw new ExpressionEvaluationException( 
                        "Expression cannot be evaluated and is not an instance of "
                        + targetType.getName(  ) );
                }
            }
            else
            {
                result = el.evaluate( expression,
                        context.unmodifiableVariableMap(  ), targetType );
            }
        }

        return result;
    }

    protected Object requireTagAttribute( String name, Class type,
        MarmaladeExecutionContext context )
        throws MissingAttributeException, ExpressionEvaluationException
    {
        return _requireTagAttribute( name, type, context );
    }

    protected Object requireTagAttribute( String name,
        MarmaladeExecutionContext context )
        throws MissingAttributeException, ExpressionEvaluationException
    {
        return _requireTagAttribute( name, Object.class, context );
    }

    private Object _requireTagAttribute( String name, Class type,
        MarmaladeExecutionContext context )
        throws MissingAttributeException, ExpressionEvaluationException
    {
        Object result = attributes.getValue( name, type, context );

        if ( result == null )
        {
            throw new MissingAttributeException( tagInfo.getElement(  ), name );
        }
        else
        {
            return result;
        }
    }

    protected MarmaladeTag requireParent( Class cls )
        throws IllegalParentException
    {
        MarmaladeTag parent = getParent(  );

        if ( parent != null )
        {
            if ( !cls.isAssignableFrom( parent.getClass(  ) ) )
            {
                throw new IllegalParentException( tagInfo.getElement(  ), cls );
            }
        }
        else
        {
            throw new IllegalParentException( tagInfo.getElement(  ), cls );
        }

        return parent;
    }

    protected MarmaladeTag requireAncestor( Class cls )
        throws IllegalAncestorException
    {
        MarmaladeTag parent = this;

        while ( ( parent = parent.getParent(  ) ) != null )
        {
            if ( cls.isAssignableFrom( parent.getClass(  ) ) )
            {
                break;
            }
        }

        if ( parent == null )
        {
            throw new IllegalAncestorException( tagInfo.getElement(  ), cls );
        }
        else
        {
            return parent;
        }
    }
}
