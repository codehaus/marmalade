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

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jelly.AbstractJellyMarmaladeTag;

import java.util.Iterator;

/**
 * @author jdcasey
 */
public class SwitchTag extends AbstractJellyMarmaladeTag
{
    public static final String ON_ATTRIBUTE = "on";
    private DefaultTag def;
    private Object testObject;

    public SwitchTag(  )
    {
    }

    public void processChildren( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        boolean blockDefaultRun = false;

        for ( Iterator it = children(  ).iterator(  ); it.hasNext(  ); )
        {
            MarmaladeTag child = ( MarmaladeTag ) it.next(  );

            if ( child instanceof CaseTag )
            {
                CaseTag tag = ( CaseTag ) child;

                tag.setTestTarget( testObject );

                boolean run = tag.conditionMatches( context );

                if ( run )
                {
                    child.execute( context );

                    if ( !tag.fallThrough(  ) )
                    {
                        blockDefaultRun = true;

                        break;
                    }
                }
            }
            else
            {
                child.execute( context );
            }
        }

        if ( !blockDefaultRun && ( def != null ) )
        {
            def.execute( context );
        }
    }

    protected boolean shouldAddChild( MarmaladeTag child )
    {
        if ( child instanceof DefaultTag )
        {
            this.def = ( DefaultTag ) child;

            return false;
        }
        else
        {
            return true;
        }
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        this.testObject = requireTagAttribute( ON_ATTRIBUTE, context );
    }

    protected boolean alwaysProcessChildren(  )
    {
        return false;
    }
}
