/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly.model;

import org.apache.commons.jelly.DynaTag;
import org.apache.commons.jelly.JellyException;
import org.apache.commons.jelly.Tag;
import org.apache.commons.jelly.TagLibrary;
import org.apache.commons.jelly.XMLOutput;
import org.codehaus.marmalade.compat.jelly.JellyCompatUncheckedException;
import org.codehaus.marmalade.compat.jelly.runtime.MarmaladeCompatJellyContext;
import org.codehaus.marmalade.compat.jelly.util.SAXAttributesWrapper;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttribute;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.AbstractPassThroughTag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author jdcasey
 */
public class JellyCompatMarmaladeTag extends AbstractPassThroughTag
{
    private TagLibrary tagLibrary;
    private Tag jellyTag;

    public JellyCompatMarmaladeTag( TagLibrary tagLibrary )
    {
        this.tagLibrary = tagLibrary;
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

            this.jellyTag = tagLibrary.createTag( mti.getElement(  ), attrs );

            System.out.println("Jelly tag: " + jellyTag);
            if ( jellyTag == null )
            {
                System.out.println("Cannot find jelly tag for: " + mti.getElement() + "; will delegate to super.");
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
                            //TODO: we're skipping this for now, but we need to at least log it.
                            if ( jellyTag instanceof DynaTag )
                            {
                                ( ( DynaTag ) jellyTag ).setAttribute( name,
                                    value );
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

                MarmaladeCompatBodyScript body = new MarmaladeCompatBodyScript( this,
                        context, bodyItems );

                jellyTag.setBody( body );

                // setup the jelly tag JellyContext wrapper around the marmalade 
                // context
                jellyTag.setContext( new MarmaladeCompatJellyContext( context,
                        el ) );

                XMLOutput output = XMLOutput.createXMLOutput( context
                        .getOutWriter(  ) );

                // execute the jelly tag.
                jellyTag.doTag( output );
            }
        }
        catch ( JellyException e )
        {
            throw new MarmaladeExecutionException( e );
        }
        catch ( JellyCompatUncheckedException e )
        {
            Throwable cause = e.getCause(  );

            if ( cause == null )
            {
                cause = e;
            }

            throw new MarmaladeExecutionException( cause );
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
}
