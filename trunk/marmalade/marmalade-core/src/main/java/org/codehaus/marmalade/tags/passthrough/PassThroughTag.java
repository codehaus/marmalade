
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
package org.codehaus.marmalade.tags.passthrough;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.ModelBuilderAttribute;
import org.codehaus.marmalade.metamodel.ModelBuilderAttributes;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.io.PrintWriter;

import java.util.Iterator;
import java.util.List;

/**
 * @author jdcasey
 */
public class PassThroughTag extends AbstractMarmaladeTag
{
    public PassThroughTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        PrintWriter out = context.getOutWriter(  );
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

        List children = children(  );

        if ( children.isEmpty(  ) )
        {
            out.println( "/>" );
        }
        else
        {
            out.println( ">" );
            processChildren( context );
            out.println( "\n</" + tagInfo.getElement(  ) + ">" );
        }
    }
}
