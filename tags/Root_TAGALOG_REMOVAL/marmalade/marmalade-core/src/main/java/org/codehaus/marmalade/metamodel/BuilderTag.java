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

            attrs.addAttribute( new DefaultRawAttribute( ns, name, value ) );

            // potentially replace with controller config from reserved ns.
            if ( MarmaladeTagBuilder.MARMALADE_RESERVED_NS.equals( ns )
                && MarmaladeTagBuilder.EXPRESSION_EVALUATOR_ATTRIBUTE.equals( 
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

        tagInfo.setSourceLine( getLocation(  ).getLine(  ) );
        tagInfo.setSourceFile( getLocation(  ).getFilename(  ) );
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
