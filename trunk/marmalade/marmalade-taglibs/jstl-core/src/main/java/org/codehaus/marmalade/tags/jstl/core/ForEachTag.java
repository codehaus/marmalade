
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
/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.AbstractLoopingTag;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jdcasey
 */
public class ForEachTag extends AbstractLoopingTag
{
    protected static final String DELIMS_CONTEXT_KEY = ForEachTag.class.getName(  )
        + ":delims";
    public static final String ITEMS_ATTRIBUTE = "items";
    public static final String VAR_ATTRIBUTE = "var";
    public static final String VAR_STATUS_ATTRIBUTE = "varStatus";
    public static final String BEGIN_ATTRIBUTE = "begin";
    public static final String END_ATTRIBUTE = "end";
    public static final String STEP_ATTRIBUTE = "step";
    private static final Integer DEFAULT_BEGIN = new Integer( 0 );
    private static final Integer DEFAULT_END = new Integer( -1 );
    private static final Integer DEFAULT_STEP = new Integer( 1 );

    public ForEachTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        Object items = requireTagAttribute( ITEMS_ATTRIBUTE, context );

        // Don't need this until later, but we want to fail early for efficiency...
        String var = ( String ) requireTagAttribute( VAR_ATTRIBUTE,
                String.class, context );

        MarmaladeAttributes attributes = getAttributes(  );

        Integer beginI = ( Integer ) attributes.getValue( BEGIN_ATTRIBUTE,
                Integer.class, context, DEFAULT_BEGIN );

        int begin = 0;

        if ( beginI != null )
        {
            begin = beginI.intValue(  );
        }

        if ( begin < 0 )
        {
            begin = 0;
        }

        Integer endI = ( Integer ) attributes.getValue( END_ATTRIBUTE,
                Integer.class, context, DEFAULT_END );

        int end = -1;

        if ( endI != null )
        {
            end = endI.intValue(  );
        }

        if ( end < -1 )
        {
            end = -1;
        }

        Integer stepI = ( Integer ) attributes.getValue( STEP_ATTRIBUTE,
                Integer.class, context, DEFAULT_STEP );

        int step = 1;

        if ( stepI != null )
        {
            step = stepI.intValue(  );
        }

        if ( step < 1 )
        {
            step = 1;
        }

        List itemList = new LinkedList(  );

        if ( items instanceof Collection )
        {
            getItemsFromCollection( ( ( Collection ) items ), itemList, begin,
                end, step );
        }
        else if ( items.getClass(  ).isArray(  ) )
        {
            getItemsFromArray( ( Object[] ) items, itemList, begin, end, step );
        }
        else if ( items instanceof String )
        {
            String delims = ( String ) context.getVariable( DELIMS_CONTEXT_KEY,
                    getExpressionEvaluator(  ) );

            if ( ( delims == null ) || ( delims.length(  ) < 1 ) )
            {
                delims = ",";
            }

            getItemsFromString( ( String ) items, delims, itemList, begin, end,
                step );
        }
        else
        {
            if ( begin == 0 )
            {
                itemList.add( new LoopStep( items, 0, 0, 1, 0 ) );
            }
        }

        if ( !itemList.isEmpty(  ) )
        {
            String varStatus = ( String ) attributes.getValue( VAR_STATUS_ATTRIBUTE,
                    String.class, context );
            LoopStep[] steps = ( LoopStep[] ) itemList.toArray( new LoopStep[itemList
                    .size(  )] );

            executeLoop( steps, var, varStatus, context );
        }
    }
}
