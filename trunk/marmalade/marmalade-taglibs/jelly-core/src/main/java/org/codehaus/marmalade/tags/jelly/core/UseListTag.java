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
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jelly.AbstractJellyMarmaladeTag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author jdcasey
 */
public class UseListTag extends AbstractJellyMarmaladeTag
{
    public static final String ITEMS_ATTRIBUTE = "items";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VAR_ATTRIBUTE = "var";

    public UseListTag(  )
    {
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        String var = ( String ) requireTagAttribute( VAR_ATTRIBUTE,
                String.class, context );
        Object result = requireTagAttribute( ITEMS_ATTRIBUTE, context );

        String className = ( String ) getAttributes(  ).getValue( CLASS_ATTRIBUTE,
                String.class, context );

        ClassLoader cloader = Thread.currentThread().getContextClassLoader();
        Class collectionClass = null;

        if ( ( className == null ) || ( className.length(  ) < 1 ) )
        {
            collectionClass = ArrayList.class;
        }
        else
        {
            try
            {
                collectionClass = cloader.loadClass( className );
            }
            catch ( ClassNotFoundException e )
            {
                throw new MarmaladeExecutionException( "cannot load list class.",
                    e );
            }
        }

        if ( !List.class.isAssignableFrom( collectionClass ) )
        {
            throw new MarmaladeExecutionException( 
                "specified class is not of type List" );
        }

        try
        {
            List impl = ( List ) collectionClass.newInstance(  );

            if ( result instanceof Collection )
            {
                impl.addAll( ( Collection ) result );
            }
            else
            {
                impl.add( result );
            }

            context.setVariable( var, impl );
        }
        catch ( InstantiationException e )
        {
            throw new MarmaladeExecutionException( "cannot instantiate list", e );
        }
        catch ( IllegalAccessException e )
        {
            throw new MarmaladeExecutionException( "cannot instantiate list", e );
        }
    }
}
