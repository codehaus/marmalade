
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
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.AbstractConditionalTag;

import java.util.Iterator;

/**
 * @author jdcasey
 */
public class ChooseTag extends AbstractMarmaladeTag
{
    private OtherwiseTag otherwise;

    public ChooseTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    public void processChildren( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        boolean conditionHit = false;

        for ( Iterator it = children(  ).iterator(  ); it.hasNext(  ); )
        {
            MarmaladeTag child = ( MarmaladeTag ) it.next(  );

            if ( child instanceof AbstractConditionalTag )
            {
                conditionHit = ( ( AbstractConditionalTag ) child )
                    .conditionMatches( context );

                if ( conditionHit )
                {
                    child.execute( context );

                    break;
                }
            }
            else
            {
                child.execute( context );
            }
        }

        if ( !conditionHit && ( otherwise != null ) )
        {
            otherwise.execute( context );
        }
    }

    protected boolean shouldAddChild( MarmaladeTag child )
    {
        if ( child instanceof OtherwiseTag )
        {
            this.otherwise = ( OtherwiseTag ) child;

            return false;
        }
        else
        {
            return true;
        }
    }
}
