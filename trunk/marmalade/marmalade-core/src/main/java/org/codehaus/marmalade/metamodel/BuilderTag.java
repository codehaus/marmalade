
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

/* Created on May 18, 2004 */
package org.codehaus.marmalade.metamodel;

import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.el.ExpressionEvaluatorFactory;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.tagalog.AbstractTag;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;

public class BuilderTag extends AbstractTag implements MarmaladeTagBuilder
{
    private MarmaladeTagInfo tagInfo = new MarmaladeTagInfo(  );
    private MarmaladeTagLibrary tagLibrary;
    private ExpressionEvaluator el;
    private ExpressionEvaluatorFactory elFactory;

    public BuilderTag( String scheme, String taglib,
        MarmaladeTagLibrary tagLibrary )
    {
        tagInfo.setScheme( scheme );
        tagInfo.setTaglib( taglib );
        this.tagLibrary = tagLibrary;
        this.elFactory = new ExpressionEvaluatorFactory(  );
    }

    public void setExpressionEvaluator( ExpressionEvaluator el )
    {
        this.el = el;
    }

    public ExpressionEvaluator getExpressionEvaluator(  )
    {
        return el;
    }

    public void setExpressionEvaluatorFactory( 
        ExpressionEvaluatorFactory elFactory )
    {
        this.elFactory = elFactory;
    }

    public ExpressionEvaluatorFactory getExpressionEvaluatorFactory(  )
    {
        return elFactory;
    }

    public MarmaladeTagLibrary getTagLibrary(  )
    {
        return tagLibrary;
    }

    public MarmaladeTagInfo getTagInfo(  )
    {
        return tagInfo;
    }

    public MarmaladeTag build(  )
        throws MarmaladeModelBuilderException
    {
        if ( tagLibrary == null )
        {
            throw new MarmaladeTagLibraryNotFoundException( tagInfo.getScheme(  ),
                tagInfo.getTaglib(  ) );
        }

        tagInfo.setExpressionEvaluator( el );

        return tagLibrary.createTag( tagInfo );
    }

    public void setParent( Tag parent )
    {
        if ( parent instanceof MarmaladeTagBuilder )
        {
            tagInfo.setParent( ( MarmaladeTagBuilder ) parent );
        }

        super.setParent( parent );
    }

    public void begin( String elementName, Attributes attributes )
        throws TagException, TagalogParseException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        for ( int i = 0; i < attributes.getAttributeCount(  ); i++ )
        {
            String ns = attributes.getNamespaceUri( i );
            String name = attributes.getName( i );
            String value = attributes.getValue( i );

            // filter marmalade:XXX attributes from the set passed on...
            if ( !MarmaladeTagBuilder.MARMALADE_RESERVED_NS.equals( ns ) )
            {
                attrs.addAttribute( new DefaultRawAttribute( ns, name, value ) );
            }

            // potentially replace with controller config from reserved ns.
            else if ( MarmaladeTagBuilder.EXPRESSION_EVALUATOR_ATTRIBUTE.equals( 
                    name ) )
            {
                ExpressionEvaluator el = null;

                if ( ( value != null ) && ( value.length(  ) > 0 ) )
                {
                    el = elFactory.getExpressionEvaluator( value );
                }

                if ( el != null )
                {
                    this.el = el;
                }
            }
        }

        tagInfo.setElement( elementName );
        tagInfo.setAttributes( attrs );

        // will set the EL impl in the build() method, to accommodate late binding in the parse process.
    }

    public synchronized void text( char[] characters, int start, int length )
        throws TagException, TagalogParseException
    {
        tagInfo.appendText( characters, start, length );
    }

    public void child( Object child )
        throws TagException, TagalogParseException
    {
        if ( !( child instanceof MarmaladeTagBuilder ) )
        {
            throw new TagalogParseException( 
                "Marmamalde Tag Builder implementations can only contain MarmaladeTagBuilder children." );
        }
        else
        {
            MarmaladeTagBuilder builder = ( MarmaladeTagBuilder ) child;

            builder.setExpressionEvaluator( el );
            tagInfo.addChild( builder );
        }
    }

    public Object end( String elementName )
        throws TagException, TagalogParseException
    {
        return this;
    }

    public boolean recycle(  )
    {
        return false;
    }
}
