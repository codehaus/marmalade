
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
package org.codehaus.marmalade.tags;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public abstract class AbstractLoopControlTag extends AbstractConditionalTag
{
    protected AbstractLoopControlTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    public boolean conditionMatches( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        Object value = getAttributes(  ).getValue( AbstractConditionalTag.TEST_ATTRIBUTE,
                Boolean.class, context );

        boolean result = true;

        if ( value != null )
        {
            if ( value instanceof Boolean )
            {
                result = ( ( Boolean ) value ).booleanValue(  );
            }
            else
            {
                String test = String.valueOf( value );

                result = Boolean.getBoolean( test );
            }
        }

        return result;
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        requireParent( LoopingTag.class );

        if ( conditionMatches( context ) )
        {
            modifyLoop( context, ( ( LoopingTag ) getParent(  ) ) );
        }
    }

    protected abstract void modifyLoop( MarmaladeExecutionContext context,
        LoopingTag parent ) throws MarmaladeExecutionException;
}
