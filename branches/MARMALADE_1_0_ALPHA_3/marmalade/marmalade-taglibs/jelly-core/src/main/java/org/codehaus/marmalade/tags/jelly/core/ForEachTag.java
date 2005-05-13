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
/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jelly.AbstractJellyLoopingTag;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jdcasey
 */
public class ForEachTag extends AbstractJellyLoopingTag
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

    public ForEachTag(  )
    {
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
