/* Created on Jul 1, 2004 */
package org.codehaus.marmalade.metamodel;

import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.model.MarmaladeControlProcessor;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.parsing.MarmaladeParsingContext;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jdcasey
 */
public class MarmaladeTagBuilder
{
    private MarmaladeTagBuilder parent;

    private List childComponents = new LinkedList();

    private MarmaladeTagInfo tagInfo;

    private MarmaladeTagLibrary tagLibrary;

    private ExpressionEvaluator expressionEvaluator;

    private MetaAttributes attributes;

    public MarmaladeTagBuilder()
    {
    }

    public void startParsing( MarmaladeParsingContext context )
    {
        this.expressionEvaluator = context.getDefaultExpressionEvaluator();
    }

    public void setParent( MarmaladeTagBuilder parent )
    {
        this.parent = parent;
    }

    public MarmaladeTagBuilder getParent()
    {
        return parent;
    }

    public void addChild( MarmaladeTagBuilder child )
    {
        childComponents.add( child );
    }

    public void addText( String text )
    {
        childComponents.add( text );
    }

    public void setTagLibrary( MarmaladeTagLibrary tagLibrary )
    {
        this.tagLibrary = tagLibrary;
    }

    public MarmaladeTag build() throws ModelBuilderException
    {
        MarmaladeTag tag = tagLibrary.createTag( tagInfo );

        tag.setTagInfo( tagInfo );
        tag.setExpressionEvaluator( expressionEvaluator );

        DefaultAttributes tagAttributes = new DefaultAttributes( attributes );

        tagAttributes.setExpressionEvaluator( expressionEvaluator );
        tag.setAttributes( tagAttributes );

        for ( Iterator it = childComponents.iterator(); it.hasNext(); )
        {
            Object childComponent = it.next();

            if ( childComponent instanceof MarmaladeTagBuilder )
            {
                MarmaladeTag child = ((MarmaladeTagBuilder) childComponent).build();

                child.setParent( tag );
                tag.addChild( child );
            }
            else
            {
                tag.appendBodyText( String.valueOf( childComponent ) );
            }
        }

        return MarmaladeControlProcessor.activateTagControls(attributes, tag);
    }

    public void setTagInfo( MarmaladeTagInfo tagInfo )
    {
        this.tagInfo = tagInfo;
    }

    public void setExpressionEvaluator( ExpressionEvaluator expressionEvaluator )
    {
        this.expressionEvaluator = expressionEvaluator;
    }

    public void setAttributes( MetaAttributes attributes )
    {
        this.attributes = attributes;
    }

    public MarmaladeTagLibrary getTagLibrary()
    {
        return tagLibrary;
    }

    public MarmaladeTagInfo getTagInfo()
    {
        return tagInfo;
    }
}