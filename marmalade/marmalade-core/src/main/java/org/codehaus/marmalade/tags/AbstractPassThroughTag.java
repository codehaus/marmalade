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
/* Created on May 25, 2004 */
package org.codehaus.marmalade.tags;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttribute;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/**
 * @author jdcasey
 */
public abstract class AbstractPassThroughTag extends AbstractMarmaladeTag
{
    private List childComponents = new LinkedList();

    protected AbstractPassThroughTag( )
    {
    }
    
    protected List getChildComponents() {
        return childComponents;
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        try {
            XmlSerializer serializer = context.getXmlSerializer();
            
            MarmaladeTagInfo tagInfo = getTagInfo(  );
            String scheme = tagInfo.getScheme();
            String taglib = tagInfo.getTaglib();
            
            String prefix = tagInfo.getPrefix();
            String oldPrefix = null;
            
            String ns = null;
            if(scheme != null && taglib != null) {
                ns = scheme + ":" + taglib;
                
                if(prefix != null) {
                    serializer.setPrefix(prefix, ns);
                    oldPrefix = serializer.getPrefix(ns, false);
                }
            }
            
            serializer.startTag(ns, tagInfo.getElement());
    
            MarmaladeAttributes attributes = getAttributes();
            for ( Iterator it = attributes.iterator(  ); it.hasNext(  ); )
            {
                MarmaladeAttribute attribute = ( MarmaladeAttribute ) it.next(  );
    
                serializer.attribute(attribute.getNamespace(), attribute.getName(), attribute.getRawValue());
            }
    
            MarmaladeTagInfo ti = getTagInfo(  );
            List children = getChildComponents();
            if ( !children.isEmpty(  ) ) {
                for ( Iterator it = children.iterator(  ); it.hasNext(  ); )
                {
                    Object childElement = ( Object ) it.next(  );
    
                    if ( childElement instanceof MarmaladeTag )
                    {
                        MarmaladeTag child = ( MarmaladeTag ) childElement;
    
                        child.execute( context );
                    }
                    else
                    {
                        String text = formatWhitespace(String.valueOf(childElement), context);
                        serializer.text(text);
                    }
                }
            }
            serializer.endTag(ns, tagInfo.getElement());
    
            if(oldPrefix != null && ns != null) {
                serializer.setPrefix(oldPrefix, ns);
            }
        }
        catch (XmlPullParserException e) {
            throw new MarmaladeExecutionException(e);
        }
        catch (IOException e) {
            throw new MarmaladeExecutionException(e);
        }
    }

    protected boolean alwaysProcessChildren(  )
    {
        return false;
    }

    public void addChild(MarmaladeTag child) {
        super.addChild(child);
        childComponents.add(child);
    }

    public void appendBodyText(String text) {
        super.appendBodyText(text);
        childComponents.add(text);
    }
}
