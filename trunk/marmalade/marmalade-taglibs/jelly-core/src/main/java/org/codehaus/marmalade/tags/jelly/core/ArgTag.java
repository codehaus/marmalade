
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

/* Created on Apr 18, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class ArgTag extends AbstractMarmaladeTag
{
    public static final String VALUE_ATTRIBUTE = "value";

    public ArgTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        requireParent( ArgParent.class );

        MarmaladeAttributes attributes = getAttributes(  );

        Object arg = attributes.getValue( VALUE_ATTRIBUTE, context );

        if ( arg == null )
        {
            arg = getBody( context );
        }

        if ( arg == null )
        {
            throw new MarmaladeExecutionException( 
                "You must specify either the \'" + VALUE_ATTRIBUTE
                + "\' attribute or body content for the arg tag." );
        }
        else
        {
            ArgParent parent = ( ArgParent ) getParent(  );

            parent.addArgument( arg );
        }
    }
}
