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
import org.codehaus.marmalade.parsetime.MarmaladeTagBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author jdcasey
 */
public class MarmaladeTagInfo
{
    private String scheme;
    private String taglib;
    private String element;
    private String prefix;
    private MetaAttributes attributes;
    private StringBuffer text;
    private MarmaladeTagBuilder parent;
    private List children = new ArrayList(  );
    private ExpressionEvaluator el;
    private int sourceLine = -1;

    /** For registering child events (child elements, text) as
     * they happen. This may be important for certain types of
     * tags.
     */
    private List childComponents = new ArrayList(  );
    private String filename;
    private String sourceFile;

    public MarmaladeTagInfo(  )
    {
    }

    public MetaAttributes getAttributes(  )
    {
        return attributes;
    }

    public void setAttributes( MetaAttributes attributes )
    {
        this.attributes = attributes;
    }

    public List getChildren(  )
    {
        return Collections.unmodifiableList( children );
    }

    public void addChild( MarmaladeTagBuilder child )
    {
        children.add( child );
        childComponents.add( child );
    }

    public String getElement(  )
    {
        return element;
    }

    public void setElement( String element )
    {
        this.element = element;
    }

    public MarmaladeTagBuilder getParent(  )
    {
        return parent;
    }

    public void setParent( MarmaladeTagBuilder parent )
    {
        this.parent = parent;
    }

    public String getScheme(  )
    {
        return scheme;
    }

    public void setScheme( String scheme )
    {
        this.scheme = scheme;
    }

    public String getTaglib(  )
    {
        return taglib;
    }

    public void setTaglib( String taglib )
    {
        this.taglib = taglib;
    }

    public String getPrefix(  )
    {
        return prefix;
    }

    public void setPrefix( String prefix )
    {
        this.prefix = prefix;
    }

    public String getText(  )
    {
        if ( text == null )
        {
            return null;
        }
        else
        {
            return text.toString(  );
        }
    }

    public synchronized void appendText( char[] c, int start, int length )
    {
        if ( text == null )
        {
            text = new StringBuffer(  );
        }

        text.append( c, start, length );
        childComponents.add( String.valueOf( c, start, length ) );
    }

    public void setExpressionEvaluator( ExpressionEvaluator el )
    {
        this.el = el;
    }

    public ExpressionEvaluator getExpressionEvaluator(  )
    {
        return el;
    }

    public int getSourceLine(  )
    {
        return sourceLine;
    }

    public void setSourceLine( int sourceLine )
    {
        this.sourceLine = sourceLine;
    }

    public List getChildComponents(  )
    {
        return Collections.unmodifiableList( childComponents );
    }

    public void setSourceFile( String sourceFile )
    {
        this.sourceFile = sourceFile;
    }

    public String getSourceFile(  )
    {
        return sourceFile;
    }
    
}
