
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
/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class SetTag extends AbstractMarmaladeTag
{
    public static final String VAR_ATTRIBUTE = "var";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String PROPERTY_ATTRIBUTE = "property";
    public static final String TARGET_ATTRIBUTE = "target";

    public SetTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        Object value = getBody( context );

        if ( value == null )
        {
            value = requireTagAttribute( VALUE_ATTRIBUTE, context );
        }

        MarmaladeAttributes attributes = getAttributes(  );
        String var = ( String ) attributes.getValue( VAR_ATTRIBUTE,
                String.class, context );

        if ( ( var != null ) && ( var.length(  ) > 0 ) )
        {
            context.setVariable( var, value );
        }
        else
        {
            setObjectProperty( value, attributes, context );
        }
    }

    private void setObjectProperty( Object value,
        MarmaladeAttributes attributes, MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        Object target = requireTagAttribute( TARGET_ATTRIBUTE, context );
        String property = ( String ) requireTagAttribute( PROPERTY_ATTRIBUTE,
                String.class, context );

        ExpressionEvaluator el = getExpressionEvaluator(  );

        el.assign( target, property, value );
    }
}
