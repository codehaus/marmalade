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
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.ModelBuilderAttribute;
import org.codehaus.marmalade.metamodel.ModelBuilderAttributes;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jelly.AbstractJellyMarmaladeTag;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author jdcasey
 */
public class WhitespaceTag extends AbstractJellyMarmaladeTag
{
    public WhitespaceTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        // save and override the whitespace preservation policy in context.
        Boolean oldPreserveWSOverride = context.preserveWhitespaceOverride(  );

        context.preserveWhitespaceOverride( Boolean.TRUE );

        StringWriter sWriter = new StringWriter(  );
        PrintWriter out = new PrintWriter( sWriter );
        MarmaladeTagInfo tagInfo = getTagInfo(  );

        out.print( "<" + tagInfo.getElement(  ) );
        out.print( " xmlns=\"" + tagInfo.getScheme(  ) + ":"
            + tagInfo.getTaglib(  ) + "\"" );

        ModelBuilderAttributes attributes = tagInfo.getAttributes(  );

        for ( Iterator it = attributes.iterator(  ); it.hasNext(  ); )
        {
            ModelBuilderAttribute attribute = ( ModelBuilderAttribute ) it.next(  );

            out.print( " " );

            String ns = attribute.getNamespace(  );

            if ( ( ns != null ) && ( ns.length(  ) > 0 ) )
            {
                out.print( ns + ":" );
            }

            out.print( attribute.getName(  ) );
            out.print( "=\"" + attribute.getValue(  ) + "\"" );
        }

        MarmaladeTagInfo ti = getTagInfo(  );
        List children = ti.getChildComponents(  );

        if ( children.isEmpty(  ) )
        {
            out.println( "/>" );
        }
        else
        {
            Map childTagMap = getChildMap(  );

            out.print( ">" );

            for ( Iterator it = children.iterator(  ); it.hasNext(  ); )
            {
                Object childElement = ( Object ) it.next(  );

                if ( childElement instanceof MarmaladeTagInfo )
                {
                    MarmaladeTag child = ( MarmaladeTag ) childTagMap.get( childElement );

                    child.execute( context );
                }
                else
                {
                    out.print( childElement );
                }
            }

            out.println( "</" + tagInfo.getElement(  ) + ">\n" );
        }

        context.getOutWriter(  ).print( formatWhitespace( 
                sWriter.getBuffer(  ).toString(  ), context ) );

        // replace the whitespace preservation override in context.
        context.preserveWhitespaceOverride( oldPreserveWSOverride );
    }

    protected boolean alwaysProcessChildren(  )
    {
        return false;
    }

    protected boolean mapChildren(  )
    {
        return true;
    }
}
