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
/* Created on Apr 20, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.TagExecutionException;
import org.codehaus.marmalade.tags.jelly.AbstractJellyMarmaladeTag;
import org.codehaus.marmalade.util.Reflector;
import org.codehaus.marmalade.util.ReflectorException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jdcasey
 */
public class NewTag extends AbstractJellyMarmaladeTag implements ArgParent
{
    public static final String CLASS_NAME_ATTRIBUTE = "className";
    public static final String USE_CTX_CLOADER_ATTRIBUTE = "useContextClassLoader";
    public static final String VAR_ATTRIBUTE = "var";
    private List args = new ArrayList(  );
    private Reflector reflector = new Reflector(  );

    public NewTag(  )
    {
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        String className = ( String ) requireTagAttribute( CLASS_NAME_ATTRIBUTE,
                String.class, context );
        String var = ( String ) requireTagAttribute( VAR_ATTRIBUTE,
                String.class, context );

        MarmaladeAttributes attrs = getAttributes(  );

        ClassLoader cloader = getClass(  ).getClassLoader(  );
        Boolean useCtxCloader = ( Boolean ) attrs.getValue( USE_CTX_CLOADER_ATTRIBUTE,
                Boolean.class, context, Boolean.FALSE );

        if ( useCtxCloader.booleanValue(  ) )
        {
            cloader = Thread.currentThread(  ).getContextClassLoader(  );
        }

        Class resultClass;

        try
        {
            resultClass = cloader.loadClass( className );
        }
        catch ( ClassNotFoundException e )
        {
            throw new TagExecutionException( getTagInfo(), "Error loading class: "
                + className, e );
        }

        Object[] params = null;
        Class[] paramTypes = null;

        if ( args.size(  ) > 0 )
        {
            params = ( Object[] ) args.toArray( new Object[args.size(  )] );
        }
        else
        {
            params = new Object[0];
        }

        Object result;

        try
        {
            result = reflector.newInstance( resultClass, params );
        }
        catch ( ReflectorException e )
        {
            throw new TagExecutionException( getTagInfo(), 
                "Error instantiating class: " + className, e );
        }

        context.setVariable( var, result );
    }

    public void addArgument( Object arg )
    {
        args.add( arg );
    }
}
