
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

/* Created on Mar 24, 2004 */
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.Map;
import java.util.TreeMap;

/** Represents base-level common functionality for all marmalade tag libraries.
 *
 * @author John Casey
 */
public abstract class AbstractMarmaladeTagLibrary implements MarmaladeTagLibrary
{
    private Map registeredTags = new TreeMap(  );

    protected AbstractMarmaladeTagLibrary(  )
    {
    }

    public MarmaladeTag createTag( MarmaladeTagInfo tagInfo )
    {
        MarmaladeTag tag = null;

        try
        {
            Class tagClass = ( Class ) registeredTags.get( tagInfo.getElement(  ) );
            Object[] params = { tagInfo };
            Class[] paramTypes = { MarmaladeTagInfo.class };
            Constructor constructor = tagClass.getConstructor( paramTypes );

            tag = ( MarmaladeTag ) constructor.newInstance( params );
        }
        catch ( SecurityException e )
        {
            throw new TagInstantiationException( e );
        }
        catch ( NoSuchMethodException e )
        {
            throw new TagInstantiationException( e );
        }
        catch ( IllegalArgumentException e )
        {
            throw new TagInstantiationException( e );
        }
        catch ( InstantiationException e )
        {
            throw new TagInstantiationException( e );
        }
        catch ( IllegalAccessException e )
        {
            throw new TagInstantiationException( e );
        }
        catch ( InvocationTargetException e )
        {
            throw new TagInstantiationException( e );
        }

        return tag;
    }

    public void registerTag( String name, Class tagClass )
    {
        registeredTags.put( name, tagClass );
    }
}
