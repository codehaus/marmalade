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
/* Created on Mar 24, 2004 */
package org.codehaus.marmalade.metamodel;

import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.parsing.ParserHint;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.Map;
import java.util.TreeMap;

/**
 * Represents base-level common functionality for all marmalade tag libraries.
 * 
 * @author John Casey
 */
public abstract class AbstractMarmaladeTagLibrary
    implements MarmaladeTagLibrary
{
    private Map registeredTags = new TreeMap();

    protected AbstractMarmaladeTagLibrary()
    {
    }

    public MarmaladeTag createTag( MarmaladeTagInfo tagInfo ) throws TagInstantiationException
    {
        MarmaladeTag tag = null;

        try
        {
            Class tagClass = (Class) registeredTags.get( tagInfo.getElement() );
            Object[] params = {};
            Class[] paramTypes = {};
            Constructor constructor = tagClass.getConstructor( paramTypes );

            tag = (MarmaladeTag) constructor.newInstance( params );
        }
        catch ( SecurityException e )
        {
            throw new TagInstantiationException( tagInfo, e );
        }
        catch ( NoSuchMethodException e )
        {
            throw new TagInstantiationException( tagInfo, e );
        }
        catch ( IllegalArgumentException e )
        {
            throw new TagInstantiationException( tagInfo, e );
        }
        catch ( InstantiationException e )
        {
            throw new TagInstantiationException( tagInfo, e );
        }
        catch ( IllegalAccessException e )
        {
            throw new TagInstantiationException( tagInfo, e );
        }
        catch ( InvocationTargetException e )
        {
            throw new TagInstantiationException( tagInfo, e );
        }

        return tag;
    }

    public void registerTag( String name, Class tagClass )
    {
        registeredTags.put( name, tagClass );
    }

    public ParserHint getParserHint( String name )
    {
        return new ParserHint().parseChildren( true );
    }
}