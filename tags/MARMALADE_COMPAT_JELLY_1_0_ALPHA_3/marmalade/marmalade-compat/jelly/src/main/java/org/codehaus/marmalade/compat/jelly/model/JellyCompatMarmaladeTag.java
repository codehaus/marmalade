/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly.model;

import org.apache.commons.jelly.DynaTag;
import org.apache.commons.jelly.JellyException;
import org.apache.commons.jelly.Tag;
import org.apache.commons.jelly.TagLibrary;
import org.apache.commons.jelly.XMLOutput;
import org.apache.commons.jelly.expression.Expression;
import org.codehaus.marmalade.compat.jelly.JellyCompatConstants;
import org.codehaus.marmalade.compat.jelly.JellyCompatUncheckedException;
import org.codehaus.marmalade.compat.jelly.metamodel.JellyCompatMarmaladeTaglib;
import org.codehaus.marmalade.compat.jelly.runtime.MarmaladeCompatJellyContext;
import org.codehaus.marmalade.compat.jelly.runtime.MarmaladeCompatJellyExpression;
import org.codehaus.marmalade.compat.jelly.util.SAXAttributesWrapper;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttribute;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.TagExecutionException;
import org.codehaus.marmalade.tags.AbstractPassThroughTag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author jdcasey
 */
public class JellyCompatMarmaladeTag extends AbstractPassThroughTag
{
    private Tag jellyTag;
    private final JellyCompatMarmaladeTaglib marmaladeTaglib;

    public JellyCompatMarmaladeTag( JellyCompatMarmaladeTaglib marmaladeTaglib )
    {
        this.marmaladeTaglib = marmaladeTaglib;
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        try
        {
            // create the jelly tag from the jelly tag library passed in via the
            // constructor
            MarmaladeTagInfo mti = getTagInfo(  );

            MarmaladeAttributes attributes = getAttributes(  );
            SAXAttributesWrapper attrs = new SAXAttributesWrapper( attributes );

            TagLibrary tagLibrary = marmaladeTaglib.getTagLibrary( mti
                    .getTaglib(  ) );

            this.jellyTag = tagLibrary.createTag( mti.getElement(  ), attrs );

            if ( jellyTag == null )
            {
                super.doExecute( context );
            }
            else
            {
                // Set the jelly tag attributes.
                ExpressionEvaluator el = getExpressionEvaluator(  );

                if ( attributes != null )
                {
                    for ( Iterator it = attributes.iterator(  );
                        it.hasNext(  ); )
                    {
                        MarmaladeAttribute attribute = ( MarmaladeAttribute ) it
                            .next(  );

                        String name = attribute.getName(  );
                        Object value = attribute.getValue( context );

                        try
                        {
                            el.assign( jellyTag, name, value );
                        }
                        catch ( ExpressionEvaluationException e )
                        {
                            // Try it as an Expression.
                            Expression expr = new MarmaladeCompatJellyExpression( value );

                            try
                            {
                                el.assign( jellyTag, name, expr );
                            }
                            catch ( ExpressionEvaluationException eInner )
                            {
                                //TODO: we're skipping this for now, but we need to at least log it.
                                if ( jellyTag instanceof DynaTag )
                                {
                                    ( ( DynaTag ) jellyTag ).setAttribute( name,
                                        value );
                                }
                            }
                        }
                        catch ( Throwable e )
                        {
                            throw new JellyCompatUncheckedException( 
                                "Failed to assign attribute [ns="
                                + attribute.getNamespace(  ) + ", prefix="
                                + attribute.getPrefix(  ) + ", name="
                                + attribute.getName(  ) + ", "
                                + attribute.getRawValue(  ) + "]", e );
                        }
                    }
                }

                // if parent marmalade tag is a jelly wrapper, setup the embedded 
                // jelly tag parent.
                MarmaladeTag parent = getParent(  );

                if ( ( parent != null )
                    && ( parent instanceof JellyCompatMarmaladeTag ) )
                {
                    JellyCompatMarmaladeTag jellyMmldParent = ( JellyCompatMarmaladeTag ) parent;

                    jellyTag.setParent( jellyMmldParent.getJellyTag(  ) );
                }

                // assemble and setup the jelly tag body, which consists of the 
                // child elements (text and tags).
                List childComponents = getChildComponents(  );
                List bodyItems = new ArrayList(  );

                for ( Iterator it = childComponents.iterator(  );
                    it.hasNext(  ); )
                {
                    bodyItems.add( it.next(  ) );
                }

                // setup the jelly tag JellyContext wrapper around the marmalade 
                // context
                MarmaladeCompatJellyContext jellyCtx = new MarmaladeCompatJellyContext( context,
                        el, marmaladeTaglib );

                jellyTag.setContext( jellyCtx );

                XMLOutput output = ( XMLOutput ) context.getVariable( JellyCompatConstants.JELLY_XML_OUTPUT_CONTEXT_VARIABLE,
                        null );

                if ( output == null )
                {
                    output = XMLOutput.createXMLOutput( context.getOutWriter(  ) );
                    context.setVariable( JellyCompatConstants.JELLY_XML_OUTPUT_CONTEXT_VARIABLE,
                        output );
                }

                MarmaladeCompatBodyScript body = new MarmaladeCompatBodyScript( this,
                        context, el, bodyItems );

                jellyTag.setBody( body );

                // execute the jelly tag.
                jellyTag.doTag( output );
            }
        }
        catch ( JellyException e )
        {
            throw new TagExecutionException( getTagInfo(), "Error executing jelly tag.", e );
        }
        catch ( JellyCompatUncheckedException e )
        {
            Throwable cause = e.getCause(  );

            if ( cause == null )
            {
                cause = e;
            }

            throw new TagExecutionException( getTagInfo(), "Error executing jelly tag.", cause );
        }
    }

    public Tag getJellyTag(  )
    {
        return jellyTag;
    }

    protected void doReset(  )
    {
        jellyTag = null;
    }

    public String formatWhitespace( String src,
        MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        return super.formatWhitespace( src, context );
    }

    protected boolean mapChildren(  )
    {
        return true;
    }

    protected boolean alwaysProcessChildren(  )
    {
        return false;
    }

    public String toString(  )
    {
        return "Jelly-compatible tag@" + hashCode(  ) + " [wrapping: "
        + ( ( jellyTag == null ) ? ( "unresolved" ) : ( String.valueOf( jellyTag ) ) )
        + "]";
    }
}
