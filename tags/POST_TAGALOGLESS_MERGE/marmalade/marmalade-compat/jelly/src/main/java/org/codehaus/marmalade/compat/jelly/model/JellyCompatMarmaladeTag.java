/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly.model;

import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.JellyException;
import org.apache.commons.jelly.Tag;
import org.apache.commons.jelly.TagLibrary;
import org.apache.commons.jelly.XMLOutput;
import org.codehaus.marmalade.compat.jelly.JellyCompatUncheckedException;
import org.codehaus.marmalade.compat.jelly.runtime.MarmaladeCompatibleJellyContext;
import org.codehaus.marmalade.compat.jelly.util.SAXAttributesWrapper;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttribute;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.runtime.ConfigurationException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.AbstractPassThroughTag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author jdcasey
 */
public class JellyCompatMarmaladeTag extends AbstractPassThroughTag
{
    private TagLibrary tagLibrary;
    private Tag jellyTag;

    public JellyCompatMarmaladeTag( TagLibrary tagLibrary )
    {
        super(  );
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

            // Set the jelly tag attributes.
            ExpressionEvaluator el = getExpressionEvaluator(  );

            for ( Iterator it = attributes.iterator(  ); it.hasNext(  ); )
            {
                MarmaladeAttribute attribute = ( MarmaladeAttribute ) it.next(  );

                el.assign( jellyTag, attribute.getName(  ),
                    attribute.getValue( context ) );
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

            for ( Iterator it = childComponents.iterator(  ); it.hasNext(  ); )
            {
                Object childElement = it.next(  );

                if ( childElement instanceof MarmaladeTagInfo )
                {
                    bodyItems.add( ( MarmaladeTag ) childElement );
                }
                else
                {
                    bodyItems.add( childElement );
                }
            }

            MarmaladeCompatibleBodyScript body = new MarmaladeCompatibleBodyScript( this,
                    context, bodyItems );

            jellyTag.setBody( body );

            // setup the jelly tag JellyContext wrapper around the marmalade 
            // context
            jellyTag.setContext( new MarmaladeCompatibleJellyContext( context,
                    el ) );

            XMLOutput output = XMLOutput.createXMLOutput( context.getOutWriter(  ) );

            // execute the jelly tag.
            jellyTag.doTag( output );
        }
        catch ( JellyException e )
        {
            throw new MarmaladeExecutionException( e );
        }
        catch ( JellyCompatUncheckedException e )
        {
            throw new MarmaladeExecutionException( e );
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
}
