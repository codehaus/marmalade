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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** Base class implementing common marmalade tag features.
 *
 * @author John Casey
 */
public abstract class AbstractMarmaladeTag implements MarmaladeTag
{
    public static final String MARMALADE_EL_PI_NAMESPACE = "marmalade-el";
    public static final String MARMALADE_EL_ATTRIBUTE = "marmalade:el";
    public static final String PRESERVE_BODY_WHITESPACE_ATTRIBUTE = "marmalade:preserve-whitespace";
    private ExpressionEvaluator el;
    private MarmaladeAttributes attributes;
    private MarmaladeTagInfo tagInfo;
    private boolean childrenProcessed = false;
    private List children = new ArrayList(  );
    private MarmaladeTag parent;
    private Map childMap;

    protected AbstractMarmaladeTag( MarmaladeTagInfo tagInfo )
    {
        this.tagInfo = tagInfo;
        this.el = tagInfo.getExpressionEvaluator(  );
        this.attributes = new DefaultAttributes( el, tagInfo.getAttributes(  ) );

        if ( mapChildren(  ) )
        {
            this.childMap = new HashMap(  );
        }
    }

    public final MarmaladeTagInfo getTagInfo(  )
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

        if ( mapChildren(  ) )
        {
            childMap.put( child.getTagInfo(  ), child );
        }
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

    protected boolean alwaysProcessChildren(  )
    {
        return true;
    }

    protected final Map getChildMap(  )
    {
        if ( childMap == null )
        {
            return null;
        }
        else
        {
            return Collections.unmodifiableMap( childMap );
        }
    }

    /** Whether this tag instance should provide a mapping of
     * MarmaladeTagInfo -&gt; MarmaladeTag for its children.
     * This may be used to retrieve the children given their
     * tagInfo objects, as in the case of replaying the
     * childComponent log from this tag's tagInfo object (to
     * output the tag as interpreted XML, for instance).
     */
    protected boolean mapChildren(  )
    {
        return false;
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

    protected final String getRawBody( MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        return formatWhitespace( tagInfo.getText(  ), context );
    }

    protected final String formatWhitespace( String src,
        MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        String formatted = src;

        Boolean preserveWSOverride = context.preserveWhitespaceOverride(  );
        boolean presWSOver = ( preserveWSOverride != null )
            ? ( preserveWSOverride.booleanValue(  ) ) : ( false );

        if ( !presWSOver && !preserveBodyWhitespace( context ) )
        {
            formatted = formatted.replaceAll( "\\s+", " " ).trim();
        }

        return formatted;
    }

    protected boolean preserveBodyWhitespace( MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        // decide from "native attribute" whether to preserve body whitespace.
        Boolean preserveWS = ( Boolean ) getAttributes(  ).getValue( PRESERVE_BODY_WHITESPACE_ATTRIBUTE,
                Boolean.class, context, Boolean.TRUE );

        return preserveWS.booleanValue(  );
    }

    private Object _getBody( MarmaladeExecutionContext context, Class targetType )
        throws ExpressionEvaluationException
    {
        String expression = tagInfo.getText(  );
        Object result = null;

        if ( ( expression != null ) && ( expression.length(  ) > 0 ) )
        {
            expression = formatWhitespace( expression, context );

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

    protected void deprecateTagAttribute( String name,
        MarmaladeExecutionContext context )
        throws MissingAttributeException, ExpressionEvaluationException
    {
        Object result = attributes.getValue( name, context );

        if ( result != null )
        {
            MarmaladeTagInfo ti = getTagInfo(  );

            context.getErrWriter(  ).println( "Attribute " + name
                + " of element " + ti.getElement(  )
                + " has been deprecated and will be ignored (file: "
                + ti.getSourceFile(  ) + ", line: " + ti.getSourceLine(  )
                + ")." );
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
