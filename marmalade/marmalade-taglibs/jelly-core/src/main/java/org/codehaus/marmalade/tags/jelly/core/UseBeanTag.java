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
/* Created on Jun 23, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttribute;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jelly.AbstractJellyMarmaladeTag;

import java.util.Iterator;

public class UseBeanTag extends AbstractJellyMarmaladeTag
    implements TargetObjectOwner
{
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VAR_ATTRIBUTE = "var";
    private Object target;

    public UseBeanTag(  )
    {
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        String var = ( String ) requireTagAttribute( VAR_ATTRIBUTE,
                String.class, context );
        Object classObj = requireTagAttribute( CLASS_ATTRIBUTE, context );

        ClassLoader cloader = Thread.currentThread().getContextClassLoader();
        Class objectClass = null;

        if ( classObj instanceof Class )
        {
            objectClass = ( Class ) classObj;
        }
        else
        {
            try
            {
                objectClass = cloader.loadClass( String.valueOf( classObj ) );
            }
            catch ( ClassNotFoundException e )
            {
                throw new MarmaladeExecutionException( "invalid bean class", e );
            }
        }

        try
        {
            this.target = objectClass.newInstance(  );
        }
        catch ( InstantiationException e )
        {
            throw new MarmaladeExecutionException( "error instantiating bean", e );
        }
        catch ( IllegalAccessException e )
        {
            throw new MarmaladeExecutionException( "error instantiating bean", e );
        }

        MarmaladeAttributes attributes = getAttributes(  );

        for ( Iterator it = attributes.iterator(  ); it.hasNext(  ); )
        {
            MarmaladeAttribute attribute = ( MarmaladeAttribute ) it.next(  );

            String attributeName = attribute.getName(  );

            if ( CLASS_ATTRIBUTE.equals( attributeName )
                || VAR_ATTRIBUTE.equals( attributeName ) )
            {
                //skip.
            }
            else
            {
                getExpressionEvaluator(  ).assign( target, attributeName,
                    attribute.getValue( context ) );
            }
        }

        context.setVariable( var, target );
    }

    protected void doReset(  )
    {
        this.target = null;
        super.doReset(  );
    }

    public Object getTarget(  )
    {
        return target;
    }
}
