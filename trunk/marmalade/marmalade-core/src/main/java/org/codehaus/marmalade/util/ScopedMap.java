
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

/* Created on Mar 25, 2004 */
package org.codehaus.marmalade.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/** Create a local copy of all mappings in the super-map.
 *
 * @author John Casey
 */
public class ScopedMap implements Map
{
    private Map superMap;
    private Map thisMap;
    private ScopedMapEntriesSet keySet;
    private ScopedMapEntriesSet valueSet;
    private ScopedMapEntriesSet entrySet;

    public ScopedMap(  )
    {
        init( Collections.EMPTY_MAP );
    }

    public ScopedMap( Map superMap )
    {
        init( superMap );
    }

    private void init( Map superMap )
    {
        Map m = superMap;

        if ( m == null )
        {
            m = Collections.EMPTY_MAP;
        }

        this.superMap = new HashMap( m );

        this.thisMap = new HashMap(  );

        this.entrySet = new ScopedMapEntriesSet( superMap, thisMap, null );
        this.keySet = new ScopedMapEntriesSet( superMap, thisMap, Boolean.TRUE );
        this.valueSet = new ScopedMapEntriesSet( superMap, thisMap,
                Boolean.FALSE );
    }

    public Map getSuperMap(  )
    {
        return superMap;
    }

    public Map getLocalMap(  )
    {
        return thisMap;
    }

    private void update(  )
    {
        entrySet.update(  );
        keySet.update(  );
        valueSet.update(  );
    }

    public int size(  )
    {
        return superMap.size(  ) + thisMap.size(  );
    }

    public void clear(  )
    {
        thisMap.clear(  );
        update(  );
    }

    public boolean isEmpty(  )
    {
        return superMap.isEmpty(  ) && thisMap.isEmpty(  );
    }

    public boolean containsKey( Object key )
    {
        return thisMap.containsKey( key ) || superMap.containsKey( key );
    }

    public boolean containsValue( Object value )
    {
        return thisMap.containsValue( value )
        || superMap.containsValue( value );
    }

    public Collection values(  )
    {
        return valueSet;
    }

    public void putAll( Map t )
    {
        boolean changed = false;

        for ( Iterator it = t.keySet(  ).iterator(  ); it.hasNext(  ); )
        {
            Object key = it.next(  );

            if ( !superMap.containsKey( key ) )
            {
                thisMap.put( key, t.get( key ) );
                changed = true;
            }
        }

        if ( changed )
        {
            update(  );
        }
    }

    public Set entrySet(  )
    {
        return entrySet;
    }

    public Set keySet(  )
    {
        return keySet;
    }

    public Object get( Object key )
    {
        Object result = thisMap.get( key );

        if ( result == null )
        {
            result = superMap.get( key );
        }

        return result;
    }

    public Object remove( Object key )
    {
        Object result = thisMap.remove( key );

        if ( result != null )
        {
            update(  );
        }

        return result;
    }

    public Object put( Object key, Object value )
    {
        Object result = null;

        result = thisMap.put( key, value );
        update(  );

        return result;
    }
}
