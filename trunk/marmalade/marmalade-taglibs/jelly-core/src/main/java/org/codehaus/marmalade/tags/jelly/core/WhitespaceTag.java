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
        Boolean oldPreserveWSOverride = context.preserveWhitespaceOverride();
        context.preserveWhitespaceOverride(Boolean.TRUE);
        
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

        MarmaladeTagInfo ti = getTagInfo();
        List children = ti.getChildComponents();

        if ( children.isEmpty(  ) )
        {
            out.println( "/>" );
        }
        else
        {
            Map childTagMap = getChildMap();
            
            out.print( ">" );
            
            for (Iterator it = children.iterator(); it.hasNext();) {
                Object childElement = (Object) it.next();
                if(childElement instanceof MarmaladeTagInfo) {
                    MarmaladeTag child = (MarmaladeTag)childTagMap.get(childElement);
                    child.execute(context);
                }
                else {
                    out.print(childElement);
                }
            }
            
            out.println( "</" + tagInfo.getElement(  ) + ">\n" );
        }
        
        context.getOutWriter().print(formatWhitespace(sWriter.getBuffer().toString(), context));
        
        // replace the whitespace preservation override in context.
        context.preserveWhitespaceOverride(oldPreserveWSOverride);
    }

    protected boolean alwaysProcessChildren() {
        return false;
    }

    protected boolean mapChildren() {
        return true;
    }
}
