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

import org.codehaus.marmalade.el.BareBonesExpressionEvaluator;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.IllegalScriptStructureException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;
import org.codehaus.marmalade.runtime.TagExecutionException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Base class implementing common marmalade tag features.
 * 
 * @author John Casey
 */
public abstract class AbstractMarmaladeTag
    implements MarmaladeTag
{
    private ExpressionEvaluator el;

    private MarmaladeAttributes attributes;

    private MarmaladeTagInfo tagInfo;

    private boolean childrenProcessed = false;

    private List children = new ArrayList();

    private MarmaladeTag parent;

    private StringBuffer bodyText;

    private boolean bodyProcessed;

    private boolean preserveBodyWhitespace = false;

    protected AbstractMarmaladeTag()
    {
    }

    public final void setAttributes( MarmaladeAttributes attributes )
    {
        this.attributes = attributes;
    }

    public final void setExpressionEvaluator( ExpressionEvaluator el )
    {
        this.el = el;
        if ( attributes != null )
        {
            attributes.setExpressionEvaluator( el );
        }
    }

    public final void setTagInfo( MarmaladeTagInfo tagInfo )
    {
        this.tagInfo = tagInfo;
    }

    public final MarmaladeTagInfo getTagInfo()
    {
        return tagInfo;
    }

    public final void setParent( MarmaladeTag parent )
    {
        this.parent = parent;
    }

    public void addChild( MarmaladeTag child )
    {
        children.add( child );
    }

    public final MarmaladeTag getParent()
    {
        return parent;
    }

    // --------------------- CONCRETE IMPLEMENTATIONS CAN OVERRIDE THESE
    // --------------------- //
    protected void doExecute( MarmaladeExecutionContext context ) throws MarmaladeExecutionException
    {
    }

    protected boolean alwaysProcessChildren()
    {
        return true;
    }

    protected boolean alwaysProcessBody()
    {
        return true;
    }

    protected void doReset()
    {
    }

    protected boolean shouldAddChild( MarmaladeTag child )
    {
        return true;
    }

    public void appendBodyText( String text )
    {
        if ( bodyText == null )
        {
            bodyText = new StringBuffer();
        }

        bodyText.append( text );
    }

    // ------------------ MARMALADE TAG IMPLEMENTATION DETAILS
    // ------------------ //
    public final void execute( MarmaladeExecutionContext context ) throws MarmaladeExecutionException
    {
        doExecute( context );

        if ( !childrenProcessed && alwaysProcessChildren() )
        {
            processChildren( context );
        }

        if ( !bodyProcessed && alwaysProcessBody() )
        {
            processBody( context );
        }

        reset();
    }

    protected final void resetChildrenProcessedFlag()
    {
        this.childrenProcessed = false;
    }

    protected final void resetBodyProcessedFlag()
    {
        this.bodyProcessed = false;
    }

    protected void processChildren( MarmaladeExecutionContext context ) throws MarmaladeExecutionException
    {
        try
        {
            for ( Iterator it = children.iterator(); it.hasNext(); )
            {
                MarmaladeTag child = (MarmaladeTag) it.next();

                child.execute( context );
            }
        }
        finally
        {
            childrenProcessed = true;
        }
    }

    protected void processBody( MarmaladeExecutionContext context ) throws MarmaladeExecutionException
    {
        try
        {
            Object bodyValue = _getBody( context, Object.class );

            if ( bodyValue != null )
            {
                String bodyContent = String.valueOf( bodyValue );

                context.getOutWriter().print( formatWhitespace( bodyContent, context ) );
            }
        }
        finally
        {
            bodyProcessed = true;
        }
    }

    protected final List children()
    {
        return Collections.unmodifiableList( children );
    }

    protected final void reset()
    {
        doReset();
        childrenProcessed = false;
    }

    public synchronized final MarmaladeAttributes getAttributes()
    {
        if ( attributes == null )
        {
            attributes = new DefaultAttributes();
        }

        return attributes;
    }

    public synchronized final ExpressionEvaluator getExpressionEvaluator()
    {
        if ( el == null )
        {
            el = new BareBonesExpressionEvaluator();
        }

        return el;
    }

    public final Object getBody( MarmaladeExecutionContext context ) throws ExpressionEvaluationException
    {
        return _getBody( context, Object.class );
    }

    public final Object getBody( MarmaladeExecutionContext context, Class targetType )
        throws ExpressionEvaluationException
    {
        return _getBody( context, targetType );
    }

    protected final String getRawBody( MarmaladeExecutionContext context ) throws ExpressionEvaluationException
    {
        if ( bodyText == null )
        {
            return null;
        }

        return formatWhitespace( bodyText.toString(), context );
    }

    protected String formatWhitespace( String src, MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        String formatted = src;

        Boolean preserveWSOverride = context.preserveWhitespaceOverride();
        boolean presWSOver = (preserveWSOverride != null) ? (preserveWSOverride.booleanValue()) : (false);

        if ( !presWSOver && !(preserveBodyWhitespace || preserveBodyWhitespace( context )) )
        {
            formatted = formatted.replaceAll( "\\s+", " " ).trim();
        }

        return formatted;
    }

    public void setPreserveBodyWhitespace( boolean preserve )
    {
        this.preserveBodyWhitespace = preserve;
    }

    protected boolean preserveBodyWhitespace( MarmaladeExecutionContext context ) throws ExpressionEvaluationException
    {
        // decide from "native attribute" whether to preserve body whitespace.
        MarmaladeAttributes attributes = getAttributes();
        Boolean preserveWS = (Boolean) attributes.getValue( MarmaladeControlDefinitions.MARMALADE_RESERVED_NS,
                                                            MarmaladeControlDefinitions.PRESERVE_BODY_WHITESPACE_ATTRIBUTE,
                                                            Boolean.class,
                                                            context,
                                                            Boolean.TRUE );

        return preserveWS.booleanValue();
    }

    private Object _getBody( MarmaladeExecutionContext context, Class targetType ) throws ExpressionEvaluationException
    {
        if ( bodyText == null )
        {
            return null;
        }

        String expression = bodyText.toString();
        Object result = null;

        if ( (expression != null) && (expression.length() > 0) )
        {
            expression = formatWhitespace( expression, context );

            if ( el == null )
            {
                el = new BareBonesExpressionEvaluator();
            }

            result = el.evaluate( expression, context.unmodifiableVariableMap(), targetType );
        }

        this.bodyProcessed = true;

        return result;
    }

    protected File getTagAttributeAsFile( String name, MarmaladeExecutionContext context, boolean requireExists )
        throws ExpressionEvaluationException, TagExecutionException
    {
        String path = (String) getAttributes().getValue( name, String.class, context );
        path = path.replaceAll( "[\\/]{2}", "/" );

        File file = new File( path );
        if ( requireExists && !file.exists() )
        {
            throw new TagExecutionException( getTagInfo(), "Specified path is required to exist but does not." );
        }

        return file;
    }

    protected File requireTagAttributeAsFile( String name, MarmaladeExecutionContext context, boolean requireExists )
        throws MissingAttributeException, ExpressionEvaluationException, TagExecutionException
    {
        String path = (String) ScriptStructureSupport.requireTagAttribute( this, name, String.class, context );
        path = path.replaceAll( "[\\/]{2}", "/" );

        File file = new File( path );
        if ( requireExists && !file.exists() )
        {
            throw new TagExecutionException( getTagInfo(), "Specified path is required to exist but does not." );
        }

        return file;
    }

    protected Object requireTagAttribute( String name, Class type, MarmaladeExecutionContext context )
        throws MissingAttributeException, ExpressionEvaluationException
    {
        return ScriptStructureSupport.requireTagAttribute( this, name, type, context );
    }

    protected Object requireTagAttribute( String name, MarmaladeExecutionContext context )
        throws MissingAttributeException, ExpressionEvaluationException
    {
        return ScriptStructureSupport.requireTagAttribute( this, name, Object.class, context );
    }

    protected void deprecateTagAttribute( String name, MarmaladeExecutionContext context )
        throws MissingAttributeException, ExpressionEvaluationException
    {
        ScriptStructureSupport.deprecateTagAttribute( this, name, context );
    }

    protected MarmaladeTag requireParent( Class cls ) throws IllegalScriptStructureException
    {
        return ScriptStructureSupport.requireParent( this, cls );
    }
    
    protected MarmaladeTag getAncestor( Class cls )
    {
        return ScriptStructureSupport.getAncestor( this, cls );
    }

    protected MarmaladeTag requireAncestor( Class cls ) throws IllegalScriptStructureException
    {
        return ScriptStructureSupport.requireAncestor( this, cls );
    }
}