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
import org.codehaus.marmalade.util.Reflector;
import org.codehaus.marmalade.util.ReflectorException;

import java.lang.reflect.Field;

/**
 * @author jdcasey
 */
public class GetStaticTag extends AbstractJellyMarmaladeTag
{
    public static final String CLASS_NAME_ATTRIBUTE = "className";
    public static final String FIELD_ATTRIBUTE = "field";
    public static final String VAR_ATTRIBUTE = "var";
    private Reflector reflector = new Reflector();

    public GetStaticTag(  )
    {
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        String className = ( String ) requireTagAttribute( CLASS_NAME_ATTRIBUTE,
                String.class, context );
        String fieldName = ( String ) requireTagAttribute( FIELD_ATTRIBUTE,
                String.class, context );
        String var = ( String ) requireTagAttribute( VAR_ATTRIBUTE,
                String.class, context );

        Class targetClass;

        try
        {
            targetClass = Class.forName( className );

            Object result = reflector.getStaticField( targetClass, fieldName );

            if ( result != null )
            {
                context.setVariable( var, result );
            }
        }
        catch ( ClassNotFoundException e )
        {
            throw new MarmaladeExecutionException( "Error loading class: "
                + className, e );
        }
        catch ( ReflectorException e )
        {
            throw new MarmaladeExecutionException( "error accessing field: "
                + fieldName + " in class: " + className );
        }
    }
}
