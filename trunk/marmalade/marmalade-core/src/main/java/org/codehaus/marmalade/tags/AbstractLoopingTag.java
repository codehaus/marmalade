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
package org.codehaus.marmalade.tags;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author jdcasey
 */
public abstract class AbstractLoopingTag
    extends AbstractMarmaladeTag
    implements LoopingTag
{
    private boolean shouldBreak = false;

    private boolean shouldContinue = false;

    protected AbstractLoopingTag()
    {
        super();
    }

    public final void breakLoop()
    {
        shouldBreak = true;
    }

    public final void continueLoop()
    {
        shouldContinue = true;
    }

    protected final boolean alwaysProcessChildren()
    {
        return false;
    }

    protected void doReset()
    {
        shouldBreak = false;
        shouldContinue = false;
    }

    protected final void executeLoop( LoopStep[] steps, String itemBinding, String statusBinding,
        MarmaladeExecutionContext context ) throws MarmaladeExecutionException
    {
        context.newScope();

        OUTER: for ( int i = 0; i < steps.length; i++ )
        {
            LoopStep step = steps[i];
            Object item = step.getItem();

            context.setVariable( itemBinding, item );

            if ( (statusBinding != null) && (statusBinding.length() > 0) )
            {
                context.setVariable( statusBinding, step );
            }

            List children = children();

            for ( Iterator it = children.iterator(); it.hasNext(); )
            {
                MarmaladeTag child = (MarmaladeTag) it.next();

                child.execute( context );

                if ( shouldBreak )
                {
                    break OUTER;
                }
                else if ( shouldContinue )
                {
                    shouldContinue = false;

                    continue OUTER;
                }
            }
        }

        context.lastScope();
    }

    protected void getItemsFromCollection( Collection items, List itemList, int begin, int end, int step )
    {
        int i = 0;

        for ( Iterator it = items.iterator(); it.hasNext(); )
        {
            Object item = it.next();

            if ( (i < begin) || ((i > end) && (end > -1)) || ((i % step) != 0) )
            {
                // ignore.
            }
            else
            {
                itemList.add( new LoopStep( item, begin, end, step, i ) );
            }

            i++;
        }
    }

    protected void getItemsFromString( String items, String delims, List itemList, int begin, int end, int step )
    {
        StringTokenizer st = new StringTokenizer( items, delims );
        int i = 0;

        while ( st.hasMoreTokens() )
        {
            String item = st.nextToken().trim();

            if ( (i < begin) || ((i > end) && (end > -1)) || ((i % step) != 0) )
            {
                // ignore.
            }
            else
            {
                itemList.add( new LoopStep( item, begin, end, step, i ) );
            }

            i++;
        }
    }

    protected void getItemsFromArray( Object[] itemArray, List itemList, int begin, int end, int step )
    {
        for ( int i = 0; i < itemArray.length; i++ )
        {
            Object item = itemArray[i];

            if ( (i < begin) || ((i > end) && (end > -1)) || ((i % step) != 0) )
            {
                continue;
            }
            else
            {
                itemList.add( new LoopStep( item, begin, end, step, i ) );
            }
        }
    }

    public static final class LoopStep
        implements LoopStatus
    {
        private Object item;

        private int index;

        private int begin;

        private int end;

        private int step;

        public LoopStep( Object item, int begin, int end, int step, int index )
        {
            this.item = item;
            this.index = index;
            this.begin = begin;
            this.end = end;
            this.step = step;
        }

        protected Object getItem()
        {
            return item;
        }

        public int getIndex()
        {
            return index;
        }

        public int getBegin()
        {
            return begin;
        }

        public int getEnd()
        {
            return end;
        }

        public int getStep()
        {
            return step;
        }
    }
}