
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
/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jelly.AbstractJellyMarmaladeTag;

/**
 * @author jdcasey
 */
public class CatchTag extends AbstractJellyMarmaladeTag
{
    public static final String VAR_ATTRIBUTE = "var";
    public static final String CLASS_ATTRIBUTE = "class";

    public CatchTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        Object binding = ( String ) requireTagAttribute( VAR_ATTRIBUTE, context );

        try
        {
            processChildren( context );
        }
        catch ( Throwable e )
        {
            String className = ( String ) getAttributes(  ).getValue( CLASS_ATTRIBUTE,
                    String.class, context );

            if ( ( className != null ) && ( className.length(  ) > 0 ) )
            {
                if ( e.getClass(  ).getName(  ).equals( className ) )
                {
                    context.setVariable( binding, e );
                }
                else
                {
                    throw new MarmaladeExecutionException( "Uncaught execution-phase exception.",
                        e );
                }
            }
            else
            {
                context.setVariable( binding, e );
            }
        }
    }
}
