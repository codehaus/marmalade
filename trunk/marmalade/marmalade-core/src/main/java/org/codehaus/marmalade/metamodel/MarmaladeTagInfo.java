
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
    private ModelBuilderAttributes attributes;
    private StringBuffer text;
    private MarmaladeTagBuilder parent;
    private List children = new ArrayList(  );
    private ExpressionEvaluator el;

    public MarmaladeTagInfo(  )
    {
    }

    public ModelBuilderAttributes getAttributes(  )
    {
        return attributes;
    }

    public void setAttributes( ModelBuilderAttributes attributes )
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
    }

    public void setExpressionEvaluator( ExpressionEvaluator el )
    {
        this.el = el;
    }

    public ExpressionEvaluator getExpressionEvaluator(  )
    {
        return el;
    }
}
